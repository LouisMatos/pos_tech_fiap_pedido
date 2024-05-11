package br.com.postechfiap.jlapppedido.infra.produto.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.postechfiap.jlapppedido.domain.produto.dto.ProdutoDTO;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.usecase.produto.ProdutoUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/produtos")
public class ProdutoController {

  private final ProdutoUseCase produtoUseCase;

  private final Logger log;

  public ProdutoController(ProdutoUseCase produtoUseCase, Logger log) {
    this.produtoUseCase = produtoUseCase;
    this.log = log;
  }

  @PostMapping
  public ResponseEntity<ProdutoDTO> inserirProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
    log.info("Iniciando o cadastro de Produto");
    return ResponseEntity.ok().body(produtoUseCase.inserir(produtoDTO));
  }

  @GetMapping
  public ResponseEntity<List<ProdutoDTO>> buscarTodosProdutos() {
    log.info("Iniciando a busca de todos os Produtos");
    return ResponseEntity.ok().body(produtoUseCase.buscarTodosProdutos());
  }

  @GetMapping("/{id}/categoria")
  public ResponseEntity<List<ProdutoDTO>> buscarProdutosPorCategoria(@PathVariable Long id) {
    log.info("Iniciando a busca de todos os Produtos da mesma Categoria");
    return ResponseEntity.ok().body(produtoUseCase.buscarProdutosPorCategoria(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProdutoDTO> atualizarProduto(@Valid @RequestBody ProdutoDTO produtoDTO,
      @PathVariable Long id) {
    log.info("Iniciando a atualização de Produto");
    return ResponseEntity.ok().body(produtoUseCase.atualizar(produtoDTO, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
    log.info("Iniciando a deleção de Produto");
    produtoUseCase.deletar(id);
    return ResponseEntity.noContent().build();
  }

}
