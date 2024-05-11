package br.com.postechfiap.jlapppedido.usecase.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.postechfiap.jlapppedido.domain.enums.Estado;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.ItemPedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoAcompanhamentoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.StatusPedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.gateway.IPedidoGateway;
import br.com.postechfiap.jlapppedido.domain.pedido.mapper.PedidoMapper;
import br.com.postechfiap.jlapppedido.domain.pedido.model.Pedido;
import br.com.postechfiap.jlapppedido.infra.config.mq.PedidoPublisher;
import br.com.postechfiap.jlapppedido.shared.exception.NotFoundException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.usecase.cliente.ClienteUseCase;
import br.com.postechfiap.jlapppedido.usecase.produto.ProdutoUseCase;

@Service
public class PedidoUseCase {

  private final IPedidoGateway pedidoGateway;

  private final ClienteUseCase clienteUseCase;

  private final ProdutoUseCase produtoUseCase;

  private final ItemPedidoUseCase itemPedidoUseCase;

  private final PedidoPublisher pedidoPublisher;

  private final Logger log;

  public PedidoUseCase(IPedidoGateway pedidoGateway, ClienteUseCase clienteUseCase,
      ProdutoUseCase produtoUseCase, ItemPedidoUseCase itemPedidoUseCase, Logger log,
      PedidoPublisher pedidoPublisher) {
    this.pedidoGateway = pedidoGateway;
    this.clienteUseCase = clienteUseCase;
    this.produtoUseCase = produtoUseCase;
    this.itemPedidoUseCase = itemPedidoUseCase;
    this.pedidoPublisher = pedidoPublisher;
    this.log = log;
  }

  public PedidoDTO inserir(PedidoDTO pedidoDTO) {

    log.info("Verificando se o cliente se indentificou!");
    if (!pedidoDTO.getClienteDTO().getCpf().isBlank()) {
      pedidoDTO
          .setClienteDTO(clienteUseCase.buscarClientePorCpf(pedidoDTO.getClienteDTO().getCpf()));
    } else {
      pedidoDTO.setClienteDTO(null);
      log.info("Pedido sem identificação do cliente!");
    }

    pedidoDTO.setEstado(Estado.RECEBIDO);
    pedidoDTO.setStatusPagamento(StatusPagamento.AGUARDANDO);
    pedidoDTO.setDataPedido(LocalDateTime.now());

    log.info("Convertendo para o dominio de Pedido!");
    // pedidoDTO.toPedidoDTO(pedidoGateway.inserir(PedidoMapper.toDomain(pedidoDTO)));
    pedidoGateway.inserir(PedidoMapper.toDomain(pedidoDTO));

    log.info("Processando itens do pedido!");
    for (int i = 0; i < pedidoDTO.getItemPedidoDTOs().size(); i++) {
      pedidoDTO.getItemPedidoDTOs().get(i).setProdutoDTO(produtoUseCase
          .buscarProdutoPorId(pedidoDTO.getItemPedidoDTOs().get(i).getProdutoDTO().getId()));
      pedidoDTO.getItemPedidoDTOs().get(i).setPedidoid(pedidoDTO.getId());
    }

    log.info("Incluindo itens ao pedido!");
    pedidoDTO.setItemPedidoDTOs(itemPedidoUseCase.inserir(pedidoDTO.getItemPedidoDTOs()));

    log.info("Calculando o valor do Pedido!");
    pedidoDTO.setValorPedido(calcularValorTotalPedido(pedidoDTO.getItemPedidoDTOs()));

    log.info("Gerando numero de pedido!");
    pedidoDTO.setNumeroPedido(gerarNumeroPedido());

    // pedidoDTO.toPedidoDTO(pedidoGateway.inserir(PedidoMapper.toDomain(pedidoDTO)));
    Pedido pedido = pedidoGateway.inserir(PedidoMapper.toDomain(pedidoDTO));
    log.info("{} salvo com sucesso!", pedidoDTO.toString());

    try {
      pedidoPublisher.send(pedido);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return PedidoMapper.toDTO(pedido);

  }


  public List<PedidoDTO> buscarTodos() {
    List<PedidoDTO> pedidoDTOs =
        pedidoGateway.buscarTodos().stream().map(pedido -> PedidoMapper.toDTO(pedido)).toList();

    for (int i = 0; i < pedidoDTOs.size(); i++) {
      pedidoDTOs.get(i)
          .setItemPedidoDTOs(itemPedidoUseCase.buscarItemPedido(pedidoDTOs.get(i).getId()));
    }

    log.info("Pedidos encontrados! {}", pedidoDTOs);
    return pedidoDTOs;
  }

  public StatusPedidoDTO buscarStatusPagamentoPedido(String numero_pedido) {

    PedidoDTO dto =
        PedidoMapper.toDTO(pedidoGateway.buscarStatusPagamentoPedido(numero_pedido).orElseThrow(
            () -> new NotFoundException("Pedido: " + numero_pedido + " não foi encontrado!")));

    log.info("Pedido encontrado! {}", dto);

    return StatusPedidoDTO.builder().numeroPedido(dto.getNumeroPedido())
        .statusPagamento(dto.getStatusPagamento()).build();
  }

  public PedidoDTO buscaPedidoNumeroPedido(String numeroPedido) {
    PedidoDTO dto =
        PedidoMapper.toDTO(pedidoGateway.buscaPedidoNumeroPedido(numeroPedido).orElseThrow(
            () -> new NotFoundException("Pedido numero " + numeroPedido + " não encontrado!")));
    log.info("Pedido: {} encontrado com sucesso!", numeroPedido);
    return dto;
  }

  public PedidoDTO atualizar(PedidoDTO pedidoDTO, String numeroPedido) {
    this.buscaPedidoNumeroPedido(numeroPedido);

    log.info("Convertendo para o dominio de Pedido!");
    Pedido pedido = PedidoMapper.toDomain(pedidoDTO);

    log.info("Atualizando o pedido de numero: {} !", numeroPedido);
    pedido.setNumeroPedido(numeroPedido);

    PedidoDTO dto = PedidoMapper.toDTO(pedidoGateway.atualizar(pedido));
    log.info("{} alterado com sucesso!", dto.toString());

    return dto;

  }

  public List<PedidoAcompanhamentoDTO> buscarPedidosAcompanhamento() {

    List<PedidoDTO> pedidoDTOs =
        pedidoGateway.buscarTodos().stream().map(pedido -> PedidoMapper.toDTO(pedido)).toList();

    List<PedidoAcompanhamentoDTO> lista = pedidoDTOs.stream()
        .map(pedido -> new PedidoAcompanhamentoDTO().toPedidoAcompanhamento(pedido))
        .collect((Collectors.toList()));
    log.info("Convertendo para o dominio de Acompanhamento de pedido!");

    lista.removeIf(t -> t.getEstado().estaFinalizado());
    log.info("Removendo os pedido em Estdo: Finalizado");

    lista.sort(Comparator.comparing(PedidoAcompanhamentoDTO::getEstado).reversed()
        .thenComparing(t -> t.getDataPedido()));
    log.info(
        "Ordenando pedidos na seguinte ordem [Pronto > Em Preparação > Recebido] sendo pedidos mais antigos primeiro que os mais novos");

    return lista;
  }

  private BigDecimal calcularValorTotalPedido(List<ItemPedidoDTO> itemPedidoDTOs) {
    BigDecimal valorPedido = BigDecimal.ZERO;

    for (ItemPedidoDTO itemPedidoDTO : itemPedidoDTOs) {
      valorPedido = valorPedido.add(itemPedidoDTO.getProdutoDTO().getPreco()
          .multiply(new BigDecimal(itemPedidoDTO.getQuantidade())));
    }

    log.info("Valor do Pedido: R$ {}", valorPedido);
    return valorPedido;
  }

  private String gerarNumeroPedido() {
    String numeroPedido = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
    log.info("Numero de Pedido: {}", numeroPedido);

    if (!pedidoGateway.buscarStatusPagamentoPedido(numeroPedido).isEmpty()) {
      log.info("Numero de Pedido: {} já existe gerando novo numero", numeroPedido);
      gerarNumeroPedido();
    } else {
      log.info("Numero de Pedido: {} disponivel para ser utilizado!", numeroPedido);
    }

    return numeroPedido;
  }



}
