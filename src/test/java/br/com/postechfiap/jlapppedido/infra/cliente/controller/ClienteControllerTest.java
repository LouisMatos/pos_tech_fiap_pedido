package br.com.postechfiap.jlapppedido.infra.cliente.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteDTO;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.usecase.cliente.ClienteUseCase;

class ClienteControllerTest {

  @InjectMocks
  private ClienteController clienteController;

  @Mock
  private ClienteUseCase clienteUseCase;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should insert client successfully")
  void shouldInsertClientSuccessfully() {
    ClienteDTO clienteDTO = new ClienteDTO();
    when(clienteUseCase.inserir(any(ClienteDTO.class))).thenReturn(clienteDTO);

    ResponseEntity<ClienteDTO> result = clienteController.inserir(clienteDTO);

    assertNotNull(result.getBody());
    assertEquals(200, result.getStatusCode().value());
  }

  @Test
  @DisplayName("Should return client by CPF")
  void shouldReturnClientByCPF() {
    String cpf = "12345678901";
    ClienteDTO clienteDTO = new ClienteDTO();
    when(clienteUseCase.buscarClientePorCpf(cpf)).thenReturn(clienteDTO);

    ResponseEntity<ClienteDTO> result = clienteController.buscarClientePorCpf(cpf);

    assertNotNull(result.getBody());
    assertEquals(200, result.getStatusCode().value());
  }
}
