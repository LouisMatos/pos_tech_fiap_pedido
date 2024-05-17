package br.com.postechfiap.jlapppedido.infra.cliente.gateway;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.postechfiap.jlapppedido.domain.cliente.model.Cliente;
import br.com.postechfiap.jlapppedido.infra.config.db.repository.ClienteRepository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ClienteSchema;
import br.com.postechfiap.jlapppedido.shared.exception.UnprocessableEntityException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

class ClienteGatewayTest {

  @InjectMocks
  private ClienteGateway clienteGateway;

  @Mock
  private ClienteRepository clienteRepository;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should insert client successfully")
  void shouldInsertClientSuccessfully() {
    Cliente cliente = new Cliente();
    cliente.setCpf("12345678901");
    when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.empty());
    when(clienteRepository.save(any(ClienteSchema.class))).thenReturn(new ClienteSchema());

    Cliente result = clienteGateway.inserir(cliente);

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should throw exception when client already exists")
  void shouldThrowExceptionWhenClientAlreadyExists() {
    Cliente cliente = new Cliente();
    cliente.setCpf("12345678901");
    when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.of(new ClienteSchema()));

    assertThrows(UnprocessableEntityException.class, () -> clienteGateway.inserir(cliente));
  }

  @Test
  @DisplayName("Should return client by CPF")
  void shouldReturnClientByCPF() {
    String cpf = "12345678901";
    when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(new ClienteSchema()));

    Optional<Cliente> result = clienteGateway.buscarClientePorCpf(cpf);

    assertTrue(result.isPresent());
  }

  @Test
  @DisplayName("Should return empty when client CPF not found")
  void shouldReturnEmptyWhenClientCPFNotFound() {
    String cpf = "12345678901";
    when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());

    Optional<Cliente> result = clienteGateway.buscarClientePorCpf(cpf);

    assertFalse(result.isPresent());
  }
}
