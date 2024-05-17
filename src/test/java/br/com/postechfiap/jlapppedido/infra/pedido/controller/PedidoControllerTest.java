package br.com.postechfiap.jlapppedido.infra.pedido.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoAcompanhamentoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.StatusPedidoDTO;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.usecase.pedido.PedidoUseCase;

class PedidoControllerTest {

  @InjectMocks
  private PedidoController pedidoController;

  @Mock
  private PedidoUseCase pedidoUseCase;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should insert order successfully")
  void shouldInsertOrderSuccessfully() {
    PedidoDTO pedidoDTO = new PedidoDTO();
    when(pedidoUseCase.inserir(any(PedidoDTO.class))).thenReturn(pedidoDTO);

    ResponseEntity<PedidoDTO> result = pedidoController.inserir(pedidoDTO);

    assertNotNull(result.getBody());
    assertEquals(200, result.getStatusCode().value());
  }

  @Test
  @DisplayName("Should return all orders")
  void shouldReturnAllOrders() {
    List<PedidoDTO> pedidos = Collections.singletonList(new PedidoDTO());
    when(pedidoUseCase.buscarTodos()).thenReturn(pedidos);

    ResponseEntity<List<PedidoDTO>> result = pedidoController.buscarTodos();

    assertNotNull(result.getBody());
    assertFalse(result.getBody().isEmpty());
    assertEquals(200, result.getStatusCode().value());
  }

  @Test
  @DisplayName("Should return order payment status")
  void shouldReturnOrderPaymentStatus() {
    String numero_pedido = "123";
    StatusPedidoDTO statusPedidoDTO = StatusPedidoDTO.builder().numeroPedido(numero_pedido)
        .statusPagamento(StatusPagamento.APROVADO).build();
    when(pedidoUseCase.buscarStatusPagamentoPedido(numero_pedido)).thenReturn(statusPedidoDTO);

    ResponseEntity<StatusPedidoDTO> result =
        pedidoController.buscarStatusPagamentoPedido(numero_pedido);

    assertNotNull(result.getBody());
    assertEquals(200, result.getStatusCode().value());
  }

  @Test
  @DisplayName("Should return order tracking")
  void shouldReturnOrderTracking() {
    List<PedidoAcompanhamentoDTO> acompanhamentos =
        Collections.singletonList(new PedidoAcompanhamentoDTO());
    when(pedidoUseCase.buscarPedidosAcompanhamento()).thenReturn(acompanhamentos);

    ResponseEntity<List<PedidoAcompanhamentoDTO>> result =
        pedidoController.buscarPedidosAcompanhamento();

    assertNotNull(result.getBody());
    assertFalse(result.getBody().isEmpty());
    assertEquals(200, result.getStatusCode().value());
  }
}
