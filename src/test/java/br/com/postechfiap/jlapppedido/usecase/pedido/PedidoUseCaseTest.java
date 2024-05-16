package br.com.postechfiap.jlapppedido.usecase.pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.ItemPedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.StatusPedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.gateway.IPedidoGateway;
import br.com.postechfiap.jlapppedido.domain.pedido.model.Pedido;
import br.com.postechfiap.jlapppedido.domain.produto.dto.ProdutoDTO;
import br.com.postechfiap.jlapppedido.infra.config.mq.PedidoPublisher;
import br.com.postechfiap.jlapppedido.shared.exception.NotFoundException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.usecase.cliente.ClienteUseCase;
import br.com.postechfiap.jlapppedido.usecase.produto.ProdutoUseCase;

public class PedidoUseCaseTest {

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
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void buscarTodosReturnsAllPedidos() {
    Pedido pedido1 = new Pedido();
    Pedido pedido2 = new Pedido();
    when(pedidoGateway.buscarTodos()).thenReturn(Arrays.asList(pedido1, pedido2));

    List<PedidoDTO> result = pedidoUseCase.buscarTodos();

    assertEquals(2, result.size());
    verify(pedidoGateway, times(1)).buscarTodos();
    verify(itemPedidoUseCase, times(2)).buscarItemPedido(any());
  }

  @Test
  public void buscarTodosReturnsEmptyListWhenNoPedidos() {
    when(pedidoGateway.buscarTodos()).thenReturn(Collections.emptyList());

    List<PedidoDTO> result = pedidoUseCase.buscarTodos();

    assertEquals(0, result.size());
    verify(pedidoGateway, times(1)).buscarTodos();
    verify(itemPedidoUseCase, times(0)).buscarItemPedido(any());
  }

  @Test
  public void buscarStatusPagamentoPedidoReturnsStatusWhenPedidoExists() {
    String numero_pedido = "123456";
    Pedido pedido = new Pedido();
    pedido.setNumeroPedido(numero_pedido);
    when(pedidoGateway.buscarStatusPagamentoPedido(numero_pedido)).thenReturn(Optional.of(pedido));

    StatusPedidoDTO result = pedidoUseCase.buscarStatusPagamentoPedido(numero_pedido);

    assertEquals(numero_pedido, result.getNumeroPedido());
    verify(pedidoGateway, times(1)).buscarStatusPagamentoPedido(numero_pedido);
  }

  @Test
  public void buscarStatusPagamentoPedidoThrowsExceptionWhenPedidoDoesNotExist() {
    String numero_pedido = "123456";
    when(pedidoGateway.buscarStatusPagamentoPedido(numero_pedido)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class,
        () -> pedidoUseCase.buscarStatusPagamentoPedido(numero_pedido));
    verify(pedidoGateway, times(1)).buscarStatusPagamentoPedido(numero_pedido);
  }

  @Test
  public void inserirReturnsPedidoWhenValid() {
    PedidoDTO pedidoDTO = new PedidoDTO();
    Pedido pedido = new Pedido();
    when(pedidoGateway.inserir(any())).thenReturn(pedido);
    when(clienteUseCase.buscarClientePorCpf(any())).thenReturn(new ClienteDTO());
    when(produtoUseCase.buscarProdutoPorId(any())).thenReturn(new ProdutoDTO());
    when(itemPedidoUseCase.inserir(any())).thenReturn(Arrays.asList(new ItemPedidoDTO()));

    PedidoDTO result = pedidoUseCase.inserir(pedidoDTO);

    assertEquals(pedidoDTO, result);
    verify(pedidoGateway, times(2)).inserir(any());
    verify(clienteUseCase, times(1)).buscarClientePorCpf(any());
    verify(produtoUseCase, times(pedidoDTO.getItemPedidoDTOs().size())).buscarProdutoPorId(any());
    verify(itemPedidoUseCase, times(1)).inserir(any());
  }

  @Test
  public void inserirReturnsPedidoWithoutClienteWhenCpfIsBlank() {
    PedidoDTO pedidoDTO = new PedidoDTO();
    pedidoDTO.setClienteDTO(new ClienteDTO(null, null, "", null));
    Pedido pedido = new Pedido();
    when(pedidoGateway.inserir(any())).thenReturn(pedido);
    when(produtoUseCase.buscarProdutoPorId(any())).thenReturn(new ProdutoDTO());
    when(itemPedidoUseCase.inserir(any())).thenReturn(Arrays.asList(new ItemPedidoDTO()));

    PedidoDTO result = pedidoUseCase.inserir(pedidoDTO);

    assertEquals(pedidoDTO, result);
    verify(pedidoGateway, times(2)).inserir(any());
    verify(clienteUseCase, times(0)).buscarClientePorCpf(any());
    verify(produtoUseCase, times(pedidoDTO.getItemPedidoDTOs().size())).buscarProdutoPorId(any());
    verify(itemPedidoUseCase, times(1)).inserir(any());
  }

}

