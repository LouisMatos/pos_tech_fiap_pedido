package br.com.postechfiap.jlapppedido.usecase.pedido;

import java.util.List;
import org.springframework.stereotype.Service;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.ItemPedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.gateway.IItemPedidoGateway;
import br.com.postechfiap.jlapppedido.domain.pedido.mapper.ItemPedidoMapper;
import br.com.postechfiap.jlapppedido.domain.pedido.model.ItemPedido;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

@Service
public class ItemPedidoUseCase {

  private final IItemPedidoGateway itemPedidoGateway;

  private final Logger log;

  public ItemPedidoUseCase(IItemPedidoGateway itemPedidoGateway, Logger log) {
    this.itemPedidoGateway = itemPedidoGateway;
    this.log = log;
  }


  public List<ItemPedidoDTO> inserir(List<ItemPedidoDTO> dtos) {
    log.info("Convertendo para o dominio de Item Pedido!");
    List<ItemPedido> itemPedidos =
        itemPedidoGateway.inserir(dtos.stream().map(ItemPedidoMapper::toDomain).toList());

    log.info("{} salvos com sucesso!", itemPedidos);
    return itemPedidos.stream().map(ItemPedidoMapper::toDTO).toList();
  }

  public List<ItemPedidoDTO> buscarItemPedido(Long idPedido) {
    List<ItemPedido> itemPedidos = itemPedidoGateway.buscarItemPedido(idPedido);

    log.info("Itens Pedido com o pedido ID: {} encontrados {} !", idPedido, itemPedidos);
    return itemPedidos.stream().map(ItemPedidoMapper::toDTO).toList();

  }

}
