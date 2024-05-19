package br.com.postechfiap.jlapppedido.infra.pedido.gateway;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.domain.cliente.model.Cliente;
import br.com.postechfiap.jlapppedido.domain.enums.Estado;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;
import br.com.postechfiap.jlapppedido.domain.pedido.model.ItemPedido;
import br.com.postechfiap.jlapppedido.domain.pedido.model.Pedido;
import br.com.postechfiap.jlapppedido.domain.produto.model.Produto;
import br.com.postechfiap.jlapppedido.infra.config.db.repository.PedidoRepository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.CategoriaSchema;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ClienteSchema;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ItemPedidoSchema;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.PedidoSchema;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ProdutoSchema;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

public class PedidoGatewayTest {

  @InjectMocks
  private PedidoGateway pedidoGateway;

  @Mock
  private PedidoRepository pedidoRepository;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }


  @Test
  public void shouldInsertNewPedido() {
    Pedido pedido = createFakePedido();
    PedidoSchema pedidoSchema = createFakePedidoSchema();
    when(pedidoRepository.save(any(PedidoSchema.class))).thenReturn(pedidoSchema);

    Pedido result = pedidoGateway.inserir(pedido);

    assertNotNull(result);
    verify(pedidoRepository, times(1)).save(any(PedidoSchema.class));
  }

  @Test
  public void shouldReturnAllPedidos() {
    List<PedidoSchema> pedidoSchemas = List.of(createFakePedidoSchema());
    when(pedidoRepository.findAll()).thenReturn(pedidoSchemas);

    List<Pedido> result = pedidoGateway.buscarTodos();

    assertFalse(result.isEmpty());
    verify(pedidoRepository, times(1)).findAll();
  }

  @Test
  public void shouldReturnPedidoByNumeroPedido() {
    String numero_pedido = "123";
    Optional<PedidoSchema> pedidoSchema = Optional.of(createFakePedidoSchema());
    when(pedidoRepository.findByNumeroPedido(numero_pedido)).thenReturn(pedidoSchema);

    Optional<Pedido> result = pedidoGateway.buscaPedidoNumeroPedido(numero_pedido);

    assertTrue(result.isPresent());
    verify(pedidoRepository, times(1)).findByNumeroPedido(numero_pedido);
  }

  @Test
  public void shouldUpdatePedido() {
    Pedido pedido = createFakePedido();
    PedidoSchema pedidoSchema = createFakePedidoSchema();
    when(pedidoRepository.save(any(PedidoSchema.class))).thenReturn(pedidoSchema);

    Pedido result = pedidoGateway.atualizar(pedido);

    assertNotNull(result);
    verify(pedidoRepository, times(1)).save(any(PedidoSchema.class));
  }

  @Test
  public void shouldUpdateEnviadoCozinha() {
    Long id = 1L;
    PedidoSchema pedidoSchema = new PedidoSchema();
    pedidoSchema.setEnviadoCozinha(false);
    when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoSchema));

    pedidoGateway.atualizarEnviadoCozinha(id, true);

    assertTrue(pedidoSchema.isEnviadoCozinha());
    verify(pedidoRepository, times(1)).findById(id);
    verify(pedidoRepository, times(1)).save(pedidoSchema);
  }

  private Pedido createFakePedido() {
    Pedido pedido = new Pedido();
    pedido.setId(1L);
    pedido.setNumeroPedido("123456");
    pedido.setValorPedido(new BigDecimal("100.00"));
    pedido.setStatusPagamento(StatusPagamento.APROVADO);
    pedido.setEstado(Estado.RECEBIDO);
    pedido.setDataPedido(LocalDateTime.parse("2024-05-16T08:47:35.446518200"));
    pedido.setEnviadoCozinha(false);

    Cliente cliente = new Cliente();
    cliente.setId(1L);
    cliente.setCpf("123.456.789-00");
    cliente.setNome("Test User");
    cliente.setEmail("teste@teste.com");
    pedido.setCliente(cliente);

    ItemPedido itemPedido = new ItemPedido();
    itemPedido.setId(1L);
    itemPedido.setPedidoid(1L);
    itemPedido.setQuantidade(1);
    itemPedido.setObservacao("Test Observation");
    itemPedido.setProduto(new Produto());

    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Test Product");
    produto.setDescricao("Test Description");
    produto.setPreco(new BigDecimal("100.00"));
    produto.setCategoria(new Categoria());
    produto.getCategoria().setId(1L);
    produto.getCategoria().setNome("Test Category");
    produto.setImagens(Arrays.asList("image1", "image2"));
    itemPedido.setProduto(produto);

    pedido.setItens(Arrays.asList(itemPedido));

    return pedido;
  }


  public PedidoSchema createFakePedidoSchema() {
    PedidoSchema pedidoSchema = new PedidoSchema();
    pedidoSchema.setId(1L);
    pedidoSchema.setNumeroPedido("123456");
    pedidoSchema.setValorPedido(new BigDecimal("100.00"));
    pedidoSchema.setStatusPagamento(StatusPagamento.APROVADO);
    pedidoSchema.setEstado(Estado.RECEBIDO);
    pedidoSchema.setDataPedido(LocalDateTime.parse("2024-05-16T08:47:35.446518200"));
    pedidoSchema.setEnviadoCozinha(false);

    ClienteSchema clienteSchema = new ClienteSchema();
    clienteSchema.setId(1L);
    clienteSchema.setCpf("123.456.789-00");
    clienteSchema.setNome("Test User");
    clienteSchema.setEmail("teste@teste.com");
    pedidoSchema.setClienteSchema(clienteSchema);

    ItemPedidoSchema itemPedidoSchema = new ItemPedidoSchema();
    itemPedidoSchema.setId(1L);
    itemPedidoSchema.setPedidoid(1L);
    itemPedidoSchema.setQuantidade(1);
    itemPedidoSchema.setObservacao("Test Observation");
    itemPedidoSchema.setProdutoSchema(new ProdutoSchema());

    ProdutoSchema produtoSchema = new ProdutoSchema();
    produtoSchema.setId(1L);
    produtoSchema.setNome("Test Product");
    produtoSchema.setDescricao("Test Description");
    produtoSchema.setPreco(new BigDecimal("100.00"));
    produtoSchema.setCategoriaSchema(new CategoriaSchema());
    produtoSchema.getCategoriaSchema().setId(1L);
    produtoSchema.getCategoriaSchema().setNome("Test Category");
    produtoSchema.setImagens(Arrays.asList("image1", "image2"));
    itemPedidoSchema.setProdutoSchema(produtoSchema);

    pedidoSchema.setItensPedidoSchema(Arrays.asList(itemPedidoSchema));

    return pedidoSchema;
  }
}
