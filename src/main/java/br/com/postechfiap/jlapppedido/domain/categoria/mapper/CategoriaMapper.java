package br.com.postechfiap.jlapppedido.domain.categoria.mapper;

import br.com.postechfiap.jlapppedido.domain.categoria.dto.CategoriaDTO;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.domain.produto.mapper.ProdutoMapper;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.CategoriaSchema;

public class CategoriaMapper {

  public static Categoria toDomain(CategoriaSchema categoriaSchema) {
    Categoria categoria = new Categoria();
    categoria.setId(categoriaSchema.getId());
    categoria.setNome(categoriaSchema.getNome());
    categoria.setDescricao(categoriaSchema.getDescricao());
    return categoria;
  }

  public static CategoriaSchema toCategoriaSchema(Categoria categoria) {
    CategoriaSchema categoriaSchema = new CategoriaSchema();
    categoriaSchema.setId(categoria.getId());
    categoriaSchema.setNome(categoria.getNome());
    categoriaSchema.setDescricao(categoria.getDescricao());
    if (categoria.getProdutos() != null) {
      categoriaSchema.setProdutoSchemas(ProdutoMapper.toListProdutoSchema(categoria.getProdutos()));
    }
    return categoriaSchema;
  }

  public static CategoriaDTO toDTO(Categoria categoria) {
    CategoriaDTO categoriaDTO = new CategoriaDTO();
    categoriaDTO.setId(categoria.getId());
    categoriaDTO.setNome(categoria.getNome());
    categoriaDTO.setDescricao(categoria.getDescricao());

    return categoriaDTO;
  }



}
