package br.com.postechfiap.jlapppedido.usecase.produto;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import br.com.postechfiap.jlapppedido.domain.categoria.gateway.ICategoriaGateway;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.domain.produto.dto.ProdutoDTO;
import br.com.postechfiap.jlapppedido.domain.produto.gateway.IProdutoGateway;
import br.com.postechfiap.jlapppedido.domain.produto.mapper.ProdutoMapper;
import br.com.postechfiap.jlapppedido.domain.produto.model.Produto;
import br.com.postechfiap.jlapppedido.shared.exception.NotFoundException;
import br.com.postechfiap.jlapppedido.shared.exception.UnprocessableEntityException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

@Service
public class ProdutoUseCase {

  private final IProdutoGateway produtoGateway;

  private final ICategoriaGateway categoriaGateway;

  private final Logger log;

  public ProdutoUseCase(IProdutoGateway produtoGateway, ICategoriaGateway categoriaGateway,
      Logger log) {
    this.produtoGateway = produtoGateway;
    this.categoriaGateway = categoriaGateway;
    this.log = log;
  }

  public ProdutoDTO inserir(ProdutoDTO produtoDTO) {

    log.info("Convertendo para o dominio de Produto!");
    Produto produto = ProdutoMapper.toDomain(produtoDTO);

    log.info("Convertendo para o dominio de Categoria!");
    Categoria categoria = categoriaGateway.buscarCategoriaPorId(produto.getCategoria().getId())
        .orElseThrow(() -> new NotFoundException(
            "Categoria com ID: " + produto.getCategoria().getId() + " não encontrado!"));

    log.info("Atribuindo {} ao novo produto!", categoria.toString());
    produto.setCategoria(categoria);

    ProdutoDTO dto = ProdutoMapper.toDTO(produtoGateway.inserir(produto));
    log.info("{} salvo com sucesso!", dto.toString());
    return dto;
  }

  public ProdutoDTO atualizar(ProdutoDTO produtoDTO, Long id) {
    this.buscarProdutoPorId(id);

    log.info("Convertendo para o dominio de Produto!");
    Produto produto = ProdutoMapper.toDomain(produtoDTO);

    log.info("Convertendo para o dominio de Categoria!");
    Categoria categoria = categoriaGateway.buscarCategoriaPorId(produto.getCategoria().getId())
        .orElseThrow(() -> new NotFoundException(
            "Categoria com ID: " + produto.getCategoria().getId() + " não encontrado!"));

    log.info("Atribuindo {} ao novo produto!", categoria.toString());
    produto.setCategoria(categoria);

    log.info("Atribuindo o ID: {} do produto que será alterado!", id);
    produto.setId(id);

    ProdutoDTO dto = ProdutoMapper.toDTO(produtoGateway.atualizar(produto));
    log.info("{} alterado com sucesso!", dto.toString());

    return dto;

  }

  public void deletar(Long id) {
    this.buscarProdutoPorId(id);
    produtoGateway.deletar(id);
    log.info("Produto com ID: {} deletado com sucesso!", id);
  }

  public List<ProdutoDTO> buscarTodosProdutos() {
    List<Produto> produtos = produtoGateway.buscarTodosProdutos();
    if (produtos.isEmpty()) {
      throw new UnprocessableEntityException("Nenhum produto cadastrado!");
    }
    log.info("Produtos encontrados! {}", produtos);
    return produtos.stream().map(ProdutoMapper::toDTO).toList();
  }

  public ProdutoDTO buscarProdutoPorId(Long id) {
    ProdutoDTO dto = ProdutoMapper.toDTO(produtoGateway.buscarProdutoPorId(id)
        .orElseThrow(() -> new NotFoundException("Produto com ID: " + id + " não encontrado!")));
    log.info("Produto com ID: {} encontrado!", id);
    return dto;
  }

  public List<ProdutoDTO> buscarProdutosPorCategoria(Long categoriaId) {

    List<Produto> produtos = produtoGateway.buscarTodosProdutos();

    log.info("Recuperando produtos com a categoria ID: {}", categoriaId);
    produtos.removeIf(p -> !Objects.equals(p.getCategoria().getId(), categoriaId));

    if (produtos.isEmpty()) {
      throw new UnprocessableEntityException("Nenhum produto cadastrado com essa categoria!");
    }

    log.info("Produtos com a categoria ID: {} encontrados {} !", categoriaId, produtos);
    return produtos.stream().map(ProdutoMapper::toDTO).toList();
  }

}
