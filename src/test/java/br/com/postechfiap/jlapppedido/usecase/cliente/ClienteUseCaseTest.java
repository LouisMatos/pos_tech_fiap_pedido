package br.com.postechfiap.jlapppedido.usecase.cliente;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteDTO;
import br.com.postechfiap.jlapppedido.domain.cliente.gateway.IClienteGateway;
import br.com.postechfiap.jlapppedido.domain.cliente.model.Cliente;
import br.com.postechfiap.jlapppedido.shared.exception.BadRequestException;
import br.com.postechfiap.jlapppedido.shared.exception.NotFoundException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

class ClienteUseCaseTest {

  @InjectMocks
  private ClienteUseCase clienteUseCase;

  @Mock
  private IClienteGateway clienteGateway;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should insert client successfully")
  void shouldInsertClientSuccessfully() {
    ClienteDTO clienteDTO = createFakeClienteDTO();
    clienteDTO.setCpf("31288312032");
    when(clienteGateway.inserir(any(Cliente.class))).thenReturn(createFakeCliente());

    ClienteDTO result = clienteUseCase.inserir(clienteDTO);

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should throw exception when CPF is invalid")
  void shouldThrowExceptionWhenCPFIsInvalid() {
    ClienteDTO clienteDTO = createFakeClienteDTO();
    clienteDTO.setCpf("31288314532");

    assertThrows(BadRequestException.class, () -> clienteUseCase.inserir(clienteDTO));
  }

  @Test
  @DisplayName("Should return client by CPF")
  void shouldReturnClientByCPF() {
    String cpf = "31288312032";
    when(clienteGateway.buscarClientePorCpf(cpf)).thenReturn(Optional.of(createFakeCliente()));

    ClienteDTO result = clienteUseCase.buscarClientePorCpf(cpf);

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should throw exception when client CPF not found")
  void shouldThrowExceptionWhenClientCPFNotFound() {
    String cpf = "31288312032";

    when(clienteGateway.buscarClientePorCpf(cpf)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> clienteUseCase.buscarClientePorCpf(cpf));
  }

  public Cliente createFakeCliente() {
    Cliente cliente = new Cliente();
    cliente.setId(1L);
    cliente.setCpf("31288312032");
    cliente.setNome("Fulano");
    cliente.setEmail("teste@teste.com");

    return cliente;
  }

  public ClienteDTO createFakeClienteDTO() {
    ClienteDTO clienteDTO = new ClienteDTO();
    clienteDTO.setId(1L);
    clienteDTO.setCpf("31288312032");
    clienteDTO.setNome("Fulano");
    clienteDTO.setEmail("teste@teste.com");
    return clienteDTO;
  }
}
