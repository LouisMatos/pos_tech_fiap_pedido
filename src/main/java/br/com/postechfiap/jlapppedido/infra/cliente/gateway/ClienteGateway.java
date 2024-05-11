package br.com.postechfiap.jlapppedido.infra.cliente.gateway;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.postechfiap.jlapppedido.domain.cliente.gateway.IClienteGateway;
import br.com.postechfiap.jlapppedido.domain.cliente.mapper.ClienteMapper;
import br.com.postechfiap.jlapppedido.domain.cliente.model.Cliente;
import br.com.postechfiap.jlapppedido.infra.config.db.repository.ClienteRepository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ClienteSchema;
import br.com.postechfiap.jlapppedido.shared.exception.UnprocessableEntityException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

@Component
public class ClienteGateway implements IClienteGateway {

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private Logger log;

  @Override
  public Cliente inserir(Cliente cliente) {

    log.info("Verificando se o cliente já existe na base!");

    Optional<Cliente> clienteExiste = this.buscarClientePorCpf(cliente.getCpf());
    if (!clienteExiste.isEmpty()) {
      throw new UnprocessableEntityException(
          "Já existe um cliente cadastrado para o cpf: " + cliente.getCpf());
    }

    log.info("Cadastrando novo cliente!");
    ClienteSchema clienteScheme = ClienteMapper.toClienteSchema(cliente);
    return ClienteMapper.toDomain(clienteRepository.save(clienteScheme));
  }

  @Override
  public Optional<Cliente> buscarClientePorCpf(String cpf) {
    log.info("Buscando cliente com o cpf {} na base de dados!", cpf);
    Optional<ClienteSchema> clienteSchema = clienteRepository.findByCpf(cpf);
    return clienteSchema.map(schema -> ClienteMapper.toDomain(schema));
  }

}
