package br.com.postechfiap.jlapppedido.domain.cliente.gateway;

import java.util.Optional;
import br.com.postechfiap.jlapppedido.domain.cliente.model.Cliente;

public interface IClienteGateway {

  public Cliente inserir(Cliente cliente);

  public Optional<Cliente> buscarClientePorCpf(String cpf);

}
