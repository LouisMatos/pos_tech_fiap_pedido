package br.com.postechfiap.jlapppedido.infra.produto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import br.com.postechfiap.jlapppedido.domain.produto.dto.ProdutoDTO;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.usecase.produto.ProdutoUseCase;

class ProdutoControllerTest {

  @InjectMocks
  private ProdutoController produtoController;

  @Mock
  private ProdutoUseCase produtoUseCase;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should insert product successfully")
  void shouldInsertProductSuccessfully() {
    ProdutoDTO produtoDTO = new ProdutoDTO();
    when(produtoUseCase.inserir(any(ProdutoDTO.class))).thenReturn(produtoDTO);

    ResponseEntity<ProdutoDTO> result = produtoController.inserirProduto(produtoDTO);

    assertNotNull(result.getBody());
    assertEquals(200, result.getStatusCode().value());
  }

  @Test
  @DisplayName("Should return all products")
  void shouldReturnAllProducts() {
    List<ProdutoDTO> produtos = Collections.singletonList(new ProdutoDTO());
    when(produtoUseCase.buscarTodosProdutos()).thenReturn(produtos);

    ResponseEntity<List<ProdutoDTO>> result = produtoController.buscarTodosProdutos();

    assertNotNull(result.getBody());
    assertFalse(result.getBody().isEmpty());
    assertEquals(200, result.getStatusCode().value());
  }

  @Test
  @DisplayName("Should return products by category")
  void shouldReturnProductsByCategory() {
    Long id = 1L;
    List<ProdutoDTO> produtos = Collections.singletonList(new ProdutoDTO());
    when(produtoUseCase.buscarProdutosPorCategoria(id)).thenReturn(produtos);

    ResponseEntity<List<ProdutoDTO>> result = produtoController.buscarProdutosPorCategoria(id);

    assertNotNull(result.getBody());
    assertFalse(result.getBody().isEmpty());
    assertEquals(200, result.getStatusCode().value());
  }

  @Test
  @DisplayName("Should update product successfully")
  void shouldUpdateProductSuccessfully() {
    Long id = 1L;
    ProdutoDTO produtoDTO = new ProdutoDTO();
    when(produtoUseCase.atualizar(any(ProdutoDTO.class), eq(id))).thenReturn(produtoDTO);

    ResponseEntity<ProdutoDTO> result = produtoController.atualizarProduto(produtoDTO, id);

    assertNotNull(result.getBody());
    assertEquals(200, result.getStatusCode().value());
  }

  @Test
  @DisplayName("Should delete product successfully")
  void shouldDeleteProductSuccessfully() {
    Long id = 1L;
    doNothing().when(produtoUseCase).deletar(id);

    ResponseEntity<Void> result = produtoController.deletarProduto(id);

    assertEquals(204, result.getStatusCode().value());
  }
}
