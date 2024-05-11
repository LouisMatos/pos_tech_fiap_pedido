package br.com.postechfiap.jlapppedido.domain.produto.mapper;

import java.util.ArrayList;
import java.util.List;
import br.com.postechfiap.jlapppedido.domain.categoria.mapper.CategoriaMapper;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.domain.produto.dto.ProdutoDTO;
import br.com.postechfiap.jlapppedido.domain.produto.model.Produto;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.CategoriaSchema;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ProdutoSchema;

public class ProdutoMapper {

  public static Produto toDomain(ProdutoSchema produtoSchema) {
    Produto produto = new Produto();
    produto.setCategoria(new Categoria());
    produto.setId(produtoSchema.getId());
    produto.setNome(produtoSchema.getNome());
    produto.setDescricao(produtoSchema.getDescricao());
    produto.setPreco(produtoSchema.getPreco());
    produto.setCategoria(CategoriaMapper.toDomain(produtoSchema.getCategoriaSchema()));
    produto.setImagens(produtoSchema.getImagens());

    return produto;
  }

  public static List<Produto> toListDomain(List<ProdutoSchema> produtoSchemas) {
    List<Produto> produtos = new ArrayList<>();
    for (ProdutoSchema produtoSchema : produtoSchemas) {
      Produto produto = toDomain(produtoSchema);
      produtos.add(produto);
    }
    return produtos;
  }

  public static ProdutoSchema toProdutoSchema(Produto produto) {
    ProdutoSchema produtoSchema = new ProdutoSchema();
    produtoSchema.setCategoriaSchema(new CategoriaSchema());
    produtoSchema.setId(produto.getId());
    produtoSchema.setNome(produto.getNome());
    produtoSchema.setDescricao(produto.getDescricao());
    produtoSchema.setPreco(produto.getPreco());
    produtoSchema.setCategoriaSchema(CategoriaMapper.toCategoriaSchema(produto.getCategoria()));
    produtoSchema.setImagens(produto.getImagens());

    return produtoSchema;
  }

  public static List<ProdutoSchema> toListProdutoSchema(List<Produto> produtos) {
    List<ProdutoSchema> produtoSchemas = new ArrayList<>();
    for (Produto produto : produtos) {
      ProdutoSchema produtoSchema = toProdutoSchema(produto);
      produtoSchemas.add(produtoSchema);
    }
    return produtoSchemas;
  }

  public static Produto toDomain(ProdutoDTO produtoDTO) {
    Produto produto = new Produto();
    produto.setCategoria(new Categoria());
    produto.setId(produtoDTO.getId());
    produto.setNome(produtoDTO.getNome());
    produto.setDescricao(produtoDTO.getDescricao());
    produto.setPreco(produtoDTO.getPreco());
    produto.getCategoria().setId(produtoDTO.getCategoriaId());
    produto.getCategoria().setNome(produtoDTO.getCategoriaNome());
    produto.setImagens(produtoDTO.getImagens());

    return produto;
  }

  public static ProdutoDTO toDTO(Produto produto) {
    ProdutoDTO produtoDTO = new ProdutoDTO();
    produtoDTO.setId(produto.getId());
    produtoDTO.setNome(produto.getNome());
    produtoDTO.setDescricao(produto.getDescricao());
    produtoDTO.setPreco(produto.getPreco());
    produtoDTO.setCategoriaId(produto.getCategoria().getId());
    produtoDTO.setCategoriaNome(produto.getCategoria().getNome());
    produtoDTO.setImagens(produto.getImagens());

    return produtoDTO;
  }

}
