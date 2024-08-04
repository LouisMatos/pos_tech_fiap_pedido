package br.com.postechfiap.jlapppedido.infra.produto.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.domain.produto.gateway.IProdutoGateway;
import br.com.postechfiap.jlapppedido.domain.produto.mapper.ProdutoMapper;
import br.com.postechfiap.jlapppedido.domain.produto.model.Produto;
import br.com.postechfiap.jlapppedido.infra.config.db.repository.ProdutoRepository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ProdutoSchema;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

@Component
public class ProdutoGateway implements IProdutoGateway {

  private final ProdutoRepository produtoRepository;

  private final Logger log;

  public ProdutoGateway(ProdutoRepository produtoRepository, Logger log) {
    this.produtoRepository = produtoRepository;
    this.log = log;
  }

  @Override
  public Produto inserir(Produto produto) {
    ProdutoSchema produtoSchema = ProdutoMapper.toProdutoSchema(produto);
    log.info("Cadastrando novo produto na base de dados!");
    return ProdutoMapper.toDomain(produtoRepository.save(produtoSchema));
  }

  @Override
  public Produto atualizar(Produto produto) {
    ProdutoSchema produtoSchema = ProdutoMapper.toProdutoSchema(produto);
    log.info("Alterando produto na base de dados!");
    return ProdutoMapper.toDomain(produtoRepository.save(produtoSchema));
  }

  @Override
  public void deletar(Long id) {
    log.info("Deletando produto com ID: {} na base de dados!", id);
    produtoRepository.deleteById(id);
  }

  @Override
  public List<Produto> buscarTodosProdutos() {
    log.info("Buscando todos os produtos cadastrados na base de dados!");
    List<ProdutoSchema> produtoSchemas = produtoRepository.findAll();
    return produtoSchemas.stream().map(ProdutoMapper::toDomain).collect(Collectors.toList());
  }

  @Override
  public Optional<Produto> buscarProdutoPorId(Long id) {
    log.info("Buscando produto com o ID: {} na base de dados!", id);
    Optional<ProdutoSchema> produtoSchema = produtoRepository.findById(id);
    return produtoSchema.map(ProdutoMapper::toDomain);
  }

  @Override
  public List<Produto> buscarProdutosPorCategoria(Categoria categoria) {
    log.info("Buscando todos os produtos cadastrados com a categoria: {} na base de dados!",
        categoria.getNome());
    List<ProdutoSchema> produtoSchema =
        produtoRepository.findCategoriaEntityById(categoria.getId());
    return produtoSchema.stream().map(ProdutoMapper::toDomain).toList();
  }

  @Override
  public List<Produto> buscarProdutosPorCategoria(Long categoriaId) {
    log.info("Buscando produto com o ID: {} na base de dados!", categoriaId);
    List<ProdutoSchema> produtoSchema = produtoRepository.findCategoriaEntityById(categoriaId);
    return produtoSchema.stream().map(ProdutoMapper::toDomain).toList();
  }

}
