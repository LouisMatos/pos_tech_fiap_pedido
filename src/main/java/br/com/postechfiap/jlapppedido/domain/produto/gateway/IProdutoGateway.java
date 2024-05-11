package br.com.postechfiap.jlapppedido.domain.produto.gateway;

import java.util.List;
import java.util.Optional;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.domain.produto.model.Produto;

public interface IProdutoGateway {

  public Produto inserir(Produto produto);

  public Produto atualizar(Produto produto);

  public void deletar(Long id);

  public List<Produto> buscarTodosProdutos();

  public List<Produto> buscarProdutosPorCategoria(Categoria categoria);

  public Optional<Produto> buscarProdutoPorId(Long id);

  public List<Produto> buscarProdutosPorCategoria(Long categoriaId);

}
