package br.com.postechfiap.jlapppedido.usecase.cliente;

import org.springframework.stereotype.Service;
import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteDTO;
import br.com.postechfiap.jlapppedido.domain.cliente.gateway.IClienteGateway;
import br.com.postechfiap.jlapppedido.domain.cliente.mapper.ClienteMapper;
import br.com.postechfiap.jlapppedido.domain.cliente.model.Cliente;
import br.com.postechfiap.jlapppedido.shared.exception.BadRequestException;
import br.com.postechfiap.jlapppedido.shared.exception.NotFoundException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.shared.utils.ValidaCPF;

@Service
public class ClienteUseCase {

  private final Logger log;

  private final IClienteGateway clienteGateway;

  public ClienteUseCase(Logger log, IClienteGateway clienteGateway) {
    this.log = log;
    this.clienteGateway = clienteGateway;
  }

  public ClienteDTO inserir(ClienteDTO clienteDTO) {

    validaCpf(clienteDTO.getCpf());

    log.info("Convertendo para Dominio Cliente");
    Cliente cliente = ClienteMapper.toDomain(clienteDTO);

    ClienteDTO dto = ClienteMapper.toDTO(clienteGateway.inserir(cliente));
    log.info("{} salvo com sucesso!", dto.toString());
    return dto;
  }

  public ClienteDTO buscarClientePorCpf(String cpf) {

    validaCpf(cpf);

    ClienteDTO dto = ClienteMapper.toDTO(clienteGateway.buscarClientePorCpf(cpf)
        .orElseThrow(() -> new NotFoundException("Cliente com o cpf " + cpf + " não encontrado!")));
    log.info("Cliente com o cpf {} encontrado!", cpf);
    return dto;
  }

  private void validaCpf(String cpf) {
    if (!ValidaCPF.isValidCPF(cpf)) {
      throw new BadRequestException("CPF " + cpf + " não é valido!");
    }
  }

}
