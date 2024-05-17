package br.com.postechfiap.jlapppedido.domain.cliente.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteDTO;
import br.com.postechfiap.jlapppedido.domain.cliente.model.Cliente;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ClienteSchema;

class ClienteMapperTest {

  @Test
  @DisplayName("Should map Cliente to ClienteDTO")
  void shouldMapDomainToDTO() {
    Cliente cliente = new Cliente();
    cliente.setId(1L);
    cliente.setNome("Test");
    cliente.setEmail("test@test.com");
    cliente.setCpf("12345678909");

    ClienteDTO clienteDTO = ClienteMapper.toDTO(cliente);

    assertEquals(cliente.getId(), clienteDTO.getId());
    assertEquals(cliente.getNome(), clienteDTO.getNome());
    assertEquals(cliente.getEmail(), clienteDTO.getEmail());
    assertEquals(cliente.getCpf(), clienteDTO.getCpf());
  }

  @Test
  @DisplayName("Should map ClienteDTO to Cliente")
  void shouldMapDTOToDomain() {
    ClienteDTO clienteDTO = new ClienteDTO();
    clienteDTO.setId(1L);
    clienteDTO.setNome("Test");
    clienteDTO.setEmail("test@test.com");
    clienteDTO.setCpf("12345678909");

    Cliente cliente = ClienteMapper.toDomain(clienteDTO);

    assertEquals(clienteDTO.getId(), cliente.getId());
    assertEquals(clienteDTO.getNome(), cliente.getNome());
    assertEquals(clienteDTO.getEmail(), cliente.getEmail());
    assertEquals(clienteDTO.getCpf(), cliente.getCpf());
  }

  @Test
  @DisplayName("Should return null when mapping null ClienteDTO to Cliente")
  void shouldReturnNullWhenMappingNullDTOToDomain() {
    Cliente cliente = ClienteMapper.toDomain((ClienteDTO) null);
    assertNull(cliente);
  }

  @Test
  @DisplayName("Should map ClienteSchema to Cliente")
  void shouldMapSchemaToDomain() {
    ClienteSchema clienteSchema = new ClienteSchema();
    clienteSchema.setId(1L);
    clienteSchema.setNome("Test");
    clienteSchema.setEmail("test@test.com");
    clienteSchema.setCpf("12345678909");

    Cliente cliente = ClienteMapper.toDomain(clienteSchema);

    assertEquals(clienteSchema.getId(), cliente.getId());
    assertEquals(clienteSchema.getNome(), cliente.getNome());
    assertEquals(clienteSchema.getEmail(), cliente.getEmail());
    assertEquals(clienteSchema.getCpf(), cliente.getCpf());
  }

  @Test
  @DisplayName("Should return null when mapping null ClienteSchema to Cliente")
  void shouldReturnNullWhenMappingNullSchemaToDomain() {
    Cliente cliente = ClienteMapper.toDomain((ClienteSchema) null);
    assertNull(cliente);
  }

  @Test
  @DisplayName("Should map Cliente to ClienteSchema")
  void shouldMapDomainToSchema() {
    Cliente cliente = new Cliente();
    cliente.setId(1L);
    cliente.setNome("Test");
    cliente.setEmail("test@test.com");
    cliente.setCpf("12345678909");

    ClienteSchema clienteSchema = ClienteMapper.toClienteSchema(cliente);

    assertEquals(cliente.getId(), clienteSchema.getId());
    assertEquals(cliente.getNome(), clienteSchema.getNome());
    assertEquals(cliente.getEmail(), clienteSchema.getEmail());
    assertEquals(cliente.getCpf(), clienteSchema.getCpf());
  }
}
