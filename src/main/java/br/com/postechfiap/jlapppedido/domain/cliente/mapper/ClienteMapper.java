package br.com.postechfiap.jlapppedido.domain.cliente.mapper;

import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteDTO;
import br.com.postechfiap.jlapppedido.domain.cliente.model.Cliente;
import br.com.postechfiap.jlapppedido.domain.pedido.mapper.PedidoMapper;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ClienteSchema;

public class ClienteMapper {

  public static ClienteDTO toDTO(Cliente cliente) {
    ClienteDTO clienteDTO = new ClienteDTO();
    clienteDTO.setId(cliente.getId());
    clienteDTO.setNome(cliente.getNome());
    clienteDTO.setEmail(cliente.getEmail());
    clienteDTO.setCpf(cliente.getCpf());
    return clienteDTO;
  }

  public static Cliente toDomain(ClienteDTO dto) {
    if (dto == null) {
      return null;
    }

    Cliente cliente = new Cliente();
    cliente.setId(dto.getId());
    cliente.setNome(dto.getNome());
    cliente.setEmail(dto.getEmail());
    cliente.setCpf(dto.getCpf());
    return cliente;
  }

  public static Cliente toDomain(ClienteSchema schema) {
    if (schema == null) {
      return null;
    }

    Cliente cliente = new Cliente();
    cliente.setId(schema.getId());
    cliente.setNome(schema.getNome());
    cliente.setEmail(schema.getEmail());
    cliente.setCpf(schema.getCpf());
    cliente.setPedidos(PedidoMapper.toListDomain(schema.getPedidosSchema()));
    return cliente;
  }

  public static ClienteSchema toClienteSchema(Cliente cliente) {
    ClienteSchema clienteSchema = new ClienteSchema();
    clienteSchema.setId(cliente.getId());
    clienteSchema.setNome(cliente.getNome());
    clienteSchema.setEmail(cliente.getEmail());
    clienteSchema.setCpf(cliente.getCpf());
    clienteSchema.setPedidosSchema(PedidoMapper.toListPedidoSchema(cliente.getPedidos()));
    return clienteSchema;
  }

}
