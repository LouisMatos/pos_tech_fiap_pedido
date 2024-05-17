package br.com.postechfiap.jlapppedido.usecase.pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.ItemPedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.gateway.IItemPedidoGateway;
import br.com.postechfiap.jlapppedido.domain.pedido.model.ItemPedido;
import br.com.postechfiap.jlapppedido.domain.produto.dto.ProdutoDTO;
import br.com.postechfiap.jlapppedido.domain.produto.model.Produto;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

class ItemPedidoUseCaseTest {

  @Mock
  private IItemPedidoGateway itemPedidoGateway;

  @Mock
  private Logger log;

  private ItemPedidoUseCase itemPedidoUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    itemPedidoUseCase = new ItemPedidoUseCase(itemPedidoGateway, log);
  }

  @Test
  @DisplayName("Should insert item orders successfully")
  void shouldInsertItemOrders() {
    ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
    itemPedidoDTO.setId(1L);
    itemPedidoDTO.setPedidoid(1L);
    itemPedidoDTO.setQuantidade(1);
    itemPedidoDTO.setObservacao("Test");

    ProdutoDTO produtoDTO = new ProdutoDTO();
    produtoDTO.setId(1L);
    produtoDTO.setNome("Test");
    produtoDTO.setPreco(new BigDecimal(10));
    itemPedidoDTO.setProdutoDTO(produtoDTO);


    ItemPedido itemPedido = new ItemPedido();
    itemPedido.setId(1L);
    itemPedido.setPedidoid(1L);
    itemPedido.setQuantidade(1);
    itemPedido.setObservacao("Test");

    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Test");
    produto.setPreco(new BigDecimal(10));

    Categoria categoria = new Categoria();
    categoria.setId(1L);
    categoria.setNome("Test");
    categoria.setDescricao("Test");
    produto.setCategoria(categoria);

    itemPedido.setProduto(produto);


    when(itemPedidoGateway.inserir(anyList())).thenReturn(Arrays.asList(itemPedido));

    List<ItemPedidoDTO> result = itemPedidoUseCase.inserir(Arrays.asList(itemPedidoDTO));

    assertEquals(1, result.size());
    verify(itemPedidoGateway, times(1)).inserir(anyList());
  }

  @Test
  @DisplayName("Should fetch item orders successfully")
  void shouldFetchItemOrders() {
    ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
    itemPedidoDTO.setId(1L);
    itemPedidoDTO.setPedidoid(1L);
    itemPedidoDTO.setQuantidade(1);
    itemPedidoDTO.setObservacao("Test");

    ProdutoDTO produtoDTO = new ProdutoDTO();
    produtoDTO.setId(1L);
    produtoDTO.setNome("Test");
    produtoDTO.setPreco(new BigDecimal(10));
    itemPedidoDTO.setProdutoDTO(produtoDTO);


    ItemPedido itemPedido = new ItemPedido();
    itemPedido.setId(1L);
    itemPedido.setPedidoid(1L);
    itemPedido.setQuantidade(1);
    itemPedido.setObservacao("Test");

    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Test");
    produto.setPreco(new BigDecimal(10));

    Categoria categoria = new Categoria();
    categoria.setId(1L);
    categoria.setNome("Test");
    categoria.setDescricao("Test");
    produto.setCategoria(categoria);

    itemPedido.setProduto(produto);

    when(itemPedidoGateway.buscarItemPedido(anyLong())).thenReturn(Arrays.asList(itemPedido));

    List<ItemPedidoDTO> result = itemPedidoUseCase.buscarItemPedido(1L);

    assertEquals(1, result.size());
    verify(itemPedidoGateway, times(1)).buscarItemPedido(anyLong());
  }
}


