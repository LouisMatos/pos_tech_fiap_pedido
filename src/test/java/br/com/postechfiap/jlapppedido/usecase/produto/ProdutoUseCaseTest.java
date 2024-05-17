package br.com.postechfiap.jlapppedido.usecase.produto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.postechfiap.jlapppedido.domain.categoria.gateway.ICategoriaGateway;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.domain.produto.dto.ProdutoDTO;
import br.com.postechfiap.jlapppedido.domain.produto.gateway.IProdutoGateway;
import br.com.postechfiap.jlapppedido.domain.produto.model.Produto;
import br.com.postechfiap.jlapppedido.shared.exception.NotFoundException;
import br.com.postechfiap.jlapppedido.shared.exception.UnprocessableEntityException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

class ProdutoUseCaseTest {

  @InjectMocks
  private ProdutoUseCase produtoUseCase;

  @Mock
  private IProdutoGateway produtoGateway;

  @Mock
  private ICategoriaGateway categoriaGateway;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should insert product successfully")
  void shouldInsertProductSuccessfully() {
    ProdutoDTO produtoDTO = createFakeProdutoDTO();
    when(produtoGateway.inserir(any(Produto.class))).thenReturn(createFakeProduto());
    when(categoriaGateway.buscarCategoriaPorId(anyLong()))
        .thenReturn(Optional.of(createFakeProduto().getCategoria()));

    ProdutoDTO result = produtoUseCase.inserir(produtoDTO);

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should update product successfully")
  void shouldUpdateProductSuccessfully() {
    ProdutoDTO produtoDTO = createFakeProdutoDTO();
    when(produtoGateway.buscarProdutoPorId(anyLong())).thenReturn(Optional.of(createFakeProduto()));
    when(categoriaGateway.buscarCategoriaPorId(anyLong()))
        .thenReturn(Optional.of(createFakeProduto().getCategoria()));
    when(produtoGateway.atualizar(any(Produto.class))).thenReturn(createFakeProduto());

    ProdutoDTO result = produtoUseCase.atualizar(produtoDTO, 1L);

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should delete product successfully")
  void shouldDeleteProductSuccessfully() {
    when(produtoGateway.buscarProdutoPorId(anyLong())).thenReturn(Optional.of(createFakeProduto()));
    doNothing().when(produtoGateway).deletar(anyLong());

    assertDoesNotThrow(() -> produtoUseCase.deletar(1L));
  }

  @Test
  @DisplayName("Should return all products")
  void shouldReturnAllProducts() {
    when(produtoGateway.buscarTodosProdutos()).thenReturn(Arrays.asList(createFakeProduto()));

    List<ProdutoDTO> result = produtoUseCase.buscarTodosProdutos();

    assertFalse(result.isEmpty());
  }

  @Test
  @DisplayName("Should return empty list when no products")
  void shouldReturnEmptyListWhenNoProducts() {
    when(produtoGateway.buscarTodosProdutos()).thenReturn(Collections.emptyList());

    assertThrows(UnprocessableEntityException.class, () -> produtoUseCase.buscarTodosProdutos());
  }

  @Test
  @DisplayName("Should return product by id")
  void shouldReturnProductById() {
    when(produtoGateway.buscarProdutoPorId(anyLong())).thenReturn(Optional.of(createFakeProduto()));

    ProdutoDTO result = produtoUseCase.buscarProdutoPorId(1L);

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should throw exception when product id not found")
  void shouldThrowExceptionWhenProductIdNotFound() {
    when(produtoGateway.buscarProdutoPorId(anyLong())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> produtoUseCase.buscarProdutoPorId(1L));
  }

  @Test
  @DisplayName("Should return products by category")
  void shouldReturnProductsByCategory() {
    when(produtoGateway.buscarTodosProdutos()).thenReturn(Arrays.asList(createFakeProduto()));

    List<ProdutoDTO> result = produtoUseCase.buscarProdutosPorCategoria(1L);

    assertFalse(result.isEmpty());
  }

  @Test
  @DisplayName("Should return empty list when no products for category")
  void shouldReturnEmptyListWhenNoProductsForCategory() {
    when(produtoGateway.buscarTodosProdutos()).thenReturn(Collections.emptyList());

    assertThrows(UnprocessableEntityException.class,
        () -> produtoUseCase.buscarProdutosPorCategoria(1L));
  }

  public Produto createFakeProduto() {
    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Produto Teste");
    produto.setDescricao("Descrição do produto teste");
    produto.setPreco(new BigDecimal("100.00"));


    Categoria categoria = new Categoria();
    categoria.setId(1L);
    categoria.setNome("Categoria Teste");
    categoria.setDescricao("Descrição da categoria teste");
    // categoria.setProdutos(Arrays.asList(produto));
    produto.setCategoria(categoria);

    return produto;
  }

  public ProdutoDTO createFakeProdutoDTO() {
    ProdutoDTO produtoDTO = new ProdutoDTO();
    produtoDTO.setId(1L);
    produtoDTO.setNome("Produto Teste");
    produtoDTO.setDescricao("Descrição do produto teste");
    produtoDTO.setPreco(new BigDecimal("100.00"));
    produtoDTO.setCategoriaId(1L);
    produtoDTO.setCategoriaNome("Categoria Teste");
    return produtoDTO;
  }
}
