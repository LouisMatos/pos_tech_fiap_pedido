package br.com.postechfiap.jlapppedido.infra.produto.gateway;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.domain.produto.model.Produto;
import br.com.postechfiap.jlapppedido.infra.config.db.repository.ProdutoRepository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.CategoriaSchema;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ProdutoSchema;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

class ProdutoGatewayTest {

  @InjectMocks
  private ProdutoGateway produtoGateway;

  @Mock
  private ProdutoRepository produtoRepository;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should insert product successfully")
  void shouldInsertProductSuccessfully() {
    Produto produto = createFakeProduto();
    when(produtoRepository.save(any(ProdutoSchema.class))).thenReturn(createFakeProdutoSchema());

    Produto result = produtoGateway.inserir(produto);

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should update product successfully")
  void shouldUpdateProductSuccessfully() {
    Produto produto = createFakeProduto();
    when(produtoRepository.save(any(ProdutoSchema.class))).thenReturn(createFakeProdutoSchema());

    Produto result = produtoGateway.atualizar(produto);

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should delete product successfully")
  void shouldDeleteProductSuccessfully() {
    Long id = 1L;
    doNothing().when(produtoRepository).deleteById(id);

    produtoGateway.deletar(id);

    verify(produtoRepository, times(1)).deleteById(id);
  }

  @Test
  @DisplayName("Should return all products")
  void shouldReturnAllProducts() {
    when(produtoRepository.findAll()).thenReturn(Arrays.asList(createFakeProdutoSchema()));

    List<Produto> result = produtoGateway.buscarTodosProdutos();

    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  @DisplayName("Should return product by ID")
  void shouldReturnProductByID() {
    Long id = 1L;
    when(produtoRepository.findById(id)).thenReturn(Optional.of(createFakeProdutoSchema()));

    Optional<Produto> result = produtoGateway.buscarProdutoPorId(id);

    assertTrue(result.isPresent());
  }

  @Test
  @DisplayName("Should return products by category")
  void shouldReturnProductsByCategory() {
    Long id = 1L;
    when(produtoRepository.findCategoriaEntityById(id))
        .thenReturn(Arrays.asList(createFakeProdutoSchema()));

    List<Produto> result = produtoGateway.buscarProdutosPorCategoria(id);

    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  public Produto createFakeProduto() {
    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Fake Product");
    produto.setDescricao("Fake Product Description");
    produto.setPreco(new BigDecimal("99.99"));

    Categoria categoria = new Categoria();
    categoria.setId(1L);
    categoria.setDescricao("Fake Category Description");
    categoria.setNome("Fake Category");

    produto.setCategoria(categoria);
    return produto;
  }

  public ProdutoSchema createFakeProdutoSchema() {

    ProdutoSchema produtoSchema = new ProdutoSchema();
    produtoSchema.setId(1L);
    produtoSchema.setNome("Fake Product");
    produtoSchema.setDescricao("Fake Product Description");
    produtoSchema.setPreco(new BigDecimal("99.99"));


    CategoriaSchema categoriaSchema = new CategoriaSchema();
    categoriaSchema.setId(1L);
    categoriaSchema.setDescricao("Fake Category Description");
    categoriaSchema.setNome("Fake Category");

    produtoSchema.setCategoriaSchema(categoriaSchema);

    return produtoSchema;
  }
}
