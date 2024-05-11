package br.com.postechfiap.jlapppedido.domain.pedido.gateway;

import java.util.List;
import br.com.postechfiap.jlapppedido.domain.pedido.model.ItemPedido;

public interface IItemPedidoGateway {

  public List<ItemPedido> inserir(List<ItemPedido> itemPedidos);

  public List<ItemPedido> buscarItemPedido(Long idPedido);

}
