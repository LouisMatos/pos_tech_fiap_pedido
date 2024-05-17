package br.com.postechfiap.jlapppedido.domain.categoria.mapper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import br.com.postechfiap.jlapppedido.domain.categoria.dto.CategoriaDTO;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.CategoriaSchema;

class CategoriaMapperTest {

  @Test
  @DisplayName("Should map CategoriaSchema to Categoria domain")
  void shouldMapCategoriaSchemaToDomain() {
    CategoriaSchema categoriaSchema = new CategoriaSchema();
    categoriaSchema.setId(1L);
    categoriaSchema.setNome("Test");
    categoriaSchema.setDescricao("Test description");

    Categoria categoria = CategoriaMapper.toDomain(categoriaSchema);

    assertEquals(categoriaSchema.getId(), categoria.getId());
    assertEquals(categoriaSchema.getNome(), categoria.getNome());
    assertEquals(categoriaSchema.getDescricao(), categoria.getDescricao());
  }

  @Test
  @DisplayName("Should map Categoria domain to CategoriaSchema")
  void shouldMapDomainToCategoriaSchema() {
    Categoria categoria = new Categoria();
    categoria.setId(1L);
    categoria.setNome("Test");
    categoria.setDescricao("Test description");

    CategoriaSchema categoriaSchema = CategoriaMapper.toCategoriaSchema(categoria);

    assertEquals(categoria.getId(), categoriaSchema.getId());
    assertEquals(categoria.getNome(), categoriaSchema.getNome());
    assertEquals(categoria.getDescricao(), categoriaSchema.getDescricao());
  }

  @Test
  @DisplayName("Should map Categoria domain to CategoriaDTO")
  void shouldMapDomainToDTO() {
    Categoria categoria = new Categoria();
    categoria.setId(1L);
    categoria.setNome("Test");
    categoria.setDescricao("Test description");

    CategoriaDTO categoriaDTO = CategoriaMapper.toDTO(categoria);

    assertEquals(categoria.getId(), categoriaDTO.getId());
    assertEquals(categoria.getNome(), categoriaDTO.getNome());
    assertEquals(categoria.getDescricao(), categoriaDTO.getDescricao());
  }
}
