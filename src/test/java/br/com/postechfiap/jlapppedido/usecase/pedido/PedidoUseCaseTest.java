package br.com.postechfiap.jlapppedido.usecase.pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteDTO;
import br.com.postechfiap.jlapppedido.domain.cliente.model.Cliente;
import br.com.postechfiap.jlapppedido.domain.enums.Estado;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.ItemPedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoAcompanhamentoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.StatusPedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.gateway.IPedidoGateway;
import br.com.postechfiap.jlapppedido.domain.pedido.model.ItemPedido;
import br.com.postechfiap.jlapppedido.domain.pedido.model.Pedido;
import br.com.postechfiap.jlapppedido.domain.produto.dto.ProdutoDTO;
import br.com.postechfiap.jlapppedido.domain.produto.model.Produto;
import br.com.postechfiap.jlapppedido.infra.config.mq.PedidoPublisher;
import br.com.postechfiap.jlapppedido.shared.exception.NotFoundException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.usecase.cliente.ClienteUseCase;
import br.com.postechfiap.jlapppedido.usecase.produto.ProdutoUseCase;

class PedidoUseCaseTest {

  @InjectMocks
  private PedidoUseCase pedidoUseCase;

  @Mock
  private IPedidoGateway pedidoGateway;

  @Mock
  private ClienteUseCase clienteUseCase;

  @Mock
  private ProdutoUseCase produtoUseCase;

  @Mock
  private ItemPedidoUseCase itemPedidoUseCase;

  @Mock
  private Logger log;

  @Mock
  private PedidoPublisher pedidoPublisher;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void buscarTodosReturnsAllPedidos() {
    Pedido pedido1 = new Pedido();
    Pedido pedido2 = new Pedido();
    when(pedidoGateway.buscarTodos()).thenReturn(Arrays.asList(pedido1, pedido2));

    List<PedidoDTO> result = pedidoUseCase.buscarTodos();

    assertEquals(2, result.size());
    verify(pedidoGateway, times(1)).buscarTodos();
    verify(itemPedidoUseCase, times(2)).buscarItemPedido(any());
  }

  @Test
  void buscarTodosReturnsEmptyListWhenNoPedidos() {
    when(pedidoGateway.buscarTodos()).thenReturn(Collections.emptyList());

    List<PedidoDTO> result = pedidoUseCase.buscarTodos();

    assertEquals(0, result.size());
    verify(pedidoGateway, times(1)).buscarTodos();
    verify(itemPedidoUseCase, times(0)).buscarItemPedido(any());
  }

  @Test
  void buscarStatusPagamentoPedidoReturnsStatusWhenPedidoExists() {
    String numero_pedido = "123456";
    Pedido pedido = new Pedido();
    pedido.setNumeroPedido(numero_pedido);
    when(pedidoGateway.buscarStatusPagamentoPedido(numero_pedido)).thenReturn(Optional.of(pedido));

    StatusPedidoDTO result = pedidoUseCase.buscarStatusPagamentoPedido(numero_pedido);

    assertEquals(numero_pedido, result.getNumeroPedido());
    verify(pedidoGateway, times(1)).buscarStatusPagamentoPedido(numero_pedido);
  }

  @Test
  void buscarStatusPagamentoPedidoThrowsExceptionWhenPedidoDoesNotExist() {
    String numero_pedido = "123456";
    when(pedidoGateway.buscarStatusPagamentoPedido(numero_pedido)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class,
        () -> pedidoUseCase.buscarStatusPagamentoPedido(numero_pedido));
    verify(pedidoGateway, times(1)).buscarStatusPagamentoPedido(numero_pedido);
  }

  @Test
  void inserirReturnsPedidoWhenValid() {
    PedidoDTO pedidoDTO = createFakePedidoDTO();
    Pedido pedido = createFakePedido();
    when(pedidoGateway.inserir(any())).thenReturn(pedido);
    when(produtoUseCase.buscarProdutoPorId(any()))
        .thenReturn(pedidoDTO.getItemPedidoDTOs().get(0).getProdutoDTO());
    when(itemPedidoUseCase.inserir(any())).thenReturn(pedidoDTO.getItemPedidoDTOs());
    when(itemPedidoUseCase.buscarItemPedido(any())).thenReturn(pedidoDTO.getItemPedidoDTOs());

    verify(pedidoGateway, times(2)).inserir(any());
    verify(clienteUseCase, times(1)).buscarClientePorCpf(any());
    verify(produtoUseCase, times(pedidoDTO.getItemPedidoDTOs().size())).buscarProdutoPorId(any());
    verify(itemPedidoUseCase, times(1)).inserir(any());
  }

  @Test
  void inserirReturnsPedidoWithoutClienteWhenCpfIsBlank() {
    PedidoDTO pedidoDTO = createFakePedidoDTO();
    pedidoDTO.getClienteDTO().setCpf("");
    Pedido pedido = createFakePedido();
    pedido.getCliente().setCpf("");
    when(pedidoGateway.inserir(any())).thenReturn(pedido);
    when(produtoUseCase.buscarProdutoPorId(any()))
        .thenReturn(pedidoDTO.getItemPedidoDTOs().get(0).getProdutoDTO());
    when(itemPedidoUseCase.inserir(any())).thenReturn(pedidoDTO.getItemPedidoDTOs());
    when(itemPedidoUseCase.buscarItemPedido(any())).thenReturn(pedidoDTO.getItemPedidoDTOs());

    verify(pedidoGateway, times(2)).inserir(any());
    verify(clienteUseCase, times(0)).buscarClientePorCpf(any());
    verify(produtoUseCase, times(pedidoDTO.getItemPedidoDTOs().size())).buscarProdutoPorId(any());
    verify(itemPedidoUseCase, times(1)).inserir(any());
  }

  @Test
  @DisplayName("Should find order by order number")
  void shouldFindOrderByOrderNumber() {
    String numeroPedido = "123456";
    Pedido pedido = new Pedido();
    pedido.setNumeroPedido(numeroPedido);
    when(pedidoGateway.buscaPedidoNumeroPedido(numeroPedido)).thenReturn(Optional.of(pedido));

    PedidoDTO result = pedidoUseCase.buscaPedidoNumeroPedido(numeroPedido);

    assertEquals(numeroPedido, result.getNumeroPedido());
  }

  @Test
  @DisplayName("Should throw exception when order number not found")
  void shouldThrowExceptionWhenOrderNumberNotFound() {
    String numeroPedido = "123456";
    when(pedidoGateway.buscaPedidoNumeroPedido(numeroPedido)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class,
        () -> pedidoUseCase.buscaPedidoNumeroPedido(numeroPedido));
  }

  @Test
  @DisplayName("Should update order")
  void shouldUpdateOrder() {
    String numeroPedido = "123456";
    PedidoDTO pedidoDTO = new PedidoDTO();
    pedidoDTO.setNumeroPedido(numeroPedido);
    Pedido pedido = new Pedido();
    pedido.setNumeroPedido(numeroPedido);
    when(pedidoGateway.buscaPedidoNumeroPedido(numeroPedido)).thenReturn(Optional.of(pedido));
    when(pedidoGateway.atualizar(any(Pedido.class))).thenReturn(pedido);

    PedidoDTO result = pedidoUseCase.atualizar(pedidoDTO, numeroPedido);

    assertEquals(numeroPedido, result.getNumeroPedido());
  }

  @Test
  @DisplayName("Should update order payment status")
  void shouldUpdateOrderPaymentStatus() {
    String numeroPedido = "123456";
    PedidoDTO pedidoDTO = createFakePedidoDTO();
    pedidoDTO.setNumeroPedido(numeroPedido);
    Pedido pedido = createFakePedido();
    pedido.setNumeroPedido(numeroPedido);
    when(pedidoGateway.buscaPedidoNumeroPedido(numeroPedido)).thenReturn(Optional.of(pedido));
    when(pedidoGateway.atualizar(any(Pedido.class))).thenReturn(pedido);

    PedidoDTO result = pedidoUseCase.atualizarStatusPagamento(numeroPedido,
        StatusPagamento.APROVADO, Estado.PRONTO);

    assertEquals(numeroPedido, result.getNumeroPedido());
    assertEquals(StatusPagamento.APROVADO, result.getStatusPagamento());
    assertEquals(Estado.RECEBIDO, result.getEstado());
  }

  @Test
  @DisplayName("Should process paid orders")
  void shouldProcessPaidOrders() throws JsonProcessingException {
    PedidoDTO pedidoDTO = createFakePedidoDTO();
    pedidoDTO.setStatusPagamento(StatusPagamento.APROVADO);
    pedidoDTO.setEstado(Estado.RECEBIDO);
    pedidoDTO.setEnviadoCozinha(false);
    when(pedidoGateway.buscarTodos()).thenReturn(Arrays.asList(createFakePedido()));
    when(itemPedidoUseCase.buscarItemPedido(anyLong()))
        .thenReturn(Arrays.asList(new ItemPedidoDTO()));
    when(itemPedidoUseCase.buscarItemPedido(anyLong()))
        .thenReturn(Arrays.asList(new ItemPedidoDTO()));
    doNothing().when(pedidoPublisher).sendPedidoCozinha(any());

    pedidoUseCase.processarPedidosPagos();

    verify(pedidoPublisher, times(1)).sendPedidoCozinha(any());
  }

  @Test
  @DisplayName("Should return all orders for tracking")
  void shouldReturnAllOrdersForTracking() {
    when(pedidoGateway.buscarTodos()).thenReturn(Arrays.asList(createFakePedido()));

    List<PedidoAcompanhamentoDTO> result = pedidoUseCase.buscarPedidosAcompanhamento();

    assertFalse(result.isEmpty());
  }

  @Test
  @DisplayName("Should return empty list when no orders for tracking")
  void shouldReturnEmptyListWhenNoOrdersForTracking() {
    when(pedidoGateway.buscarTodos()).thenReturn(Collections.emptyList());

    List<PedidoAcompanhamentoDTO> result = pedidoUseCase.buscarPedidosAcompanhamento();

    assertTrue(result.isEmpty());
  }


  private PedidoDTO createFakePedidoDTO() {
    PedidoDTO pedidoDTO = new PedidoDTO();
    pedidoDTO.setId(1L);
    pedidoDTO.setNumeroPedido("123456");
    pedidoDTO.setValorPedido(new BigDecimal("100.00"));
    pedidoDTO.setStatusPagamento(StatusPagamento.APROVADO);
    pedidoDTO.setEstado(Estado.RECEBIDO);
    pedidoDTO.setDataPedido(LocalDateTime.parse("2024-05-16T08:47:35.446518200"));
    pedidoDTO.setEnviadoCozinha(false);

    ClienteDTO clienteDTO = new ClienteDTO();
    clienteDTO.setId(1L);
    clienteDTO.setCpf("123.456.789-00");
    clienteDTO.setNome("Test User");
    clienteDTO.setEmail("teste@teste.com");
    pedidoDTO.setClienteDTO(clienteDTO);

    ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
    itemPedidoDTO.setId(1L);
    itemPedidoDTO.setPedidoid(1L);
    itemPedidoDTO.setQuantidade(1);
    itemPedidoDTO.setObservacao("Test Observation");
    pedidoDTO.setItemPedidoDTOs(Arrays.asList(itemPedidoDTO));

    ProdutoDTO produtoDTO = new ProdutoDTO();
    produtoDTO.setId(1L);
    produtoDTO.setNome("Test Product");
    produtoDTO.setDescricao("Test Description");
    produtoDTO.setPreco(new BigDecimal("100.00"));
    produtoDTO.setCategoriaId(1L);
    produtoDTO.setCategoriaNome("Test Category");
    produtoDTO.setImagens(Arrays.asList("image1", "image2"));
    itemPedidoDTO.setProdutoDTO(produtoDTO);

    pedidoDTO.setItemPedidoDTOs(Arrays.asList(itemPedidoDTO));

    return pedidoDTO;
  }

  private Pedido createFakePedido() {
    Pedido pedido = new Pedido();
    pedido.setId(1L);
    pedido.setNumeroPedido("123456");
    pedido.setValorPedido(new BigDecimal("100.00"));
    pedido.setStatusPagamento(StatusPagamento.APROVADO);
    pedido.setEstado(Estado.RECEBIDO);
    pedido.setDataPedido(LocalDateTime.parse("2024-05-16T08:47:35.446518200"));
    pedido.setEnviadoCozinha(false);

    Cliente cliente = new Cliente();
    cliente.setId(1L);
    cliente.setCpf("123.456.789-00");
    cliente.setNome("Test User");
    cliente.setEmail("teste@teste.com");
    pedido.setCliente(cliente);

    ItemPedido itemPedido = new ItemPedido();
    itemPedido.setId(1L);
    itemPedido.setPedidoid(1L);
    itemPedido.setQuantidade(1);
    itemPedido.setObservacao("Test Observation");
    itemPedido.setProduto(new Produto());

    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Test Product");
    produto.setDescricao("Test Description");
    produto.setPreco(new BigDecimal("100.00"));
    produto.setCategoria(new Categoria());
    produto.getCategoria().setId(1L);
    produto.getCategoria().setNome("Test Category");
    produto.setImagens(Arrays.asList("image1", "image2"));
    itemPedido.setProduto(produto);

    pedido.setItens(Arrays.asList(itemPedido));

    return pedido;
  }



}

