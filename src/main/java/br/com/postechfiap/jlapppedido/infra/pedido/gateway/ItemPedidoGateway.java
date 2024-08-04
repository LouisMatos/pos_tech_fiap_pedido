package br.com.postechfiap.jlapppedido.infra.pedido.gateway;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import br.com.postechfiap.jlapppedido.domain.pedido.gateway.IItemPedidoGateway;
import br.com.postechfiap.jlapppedido.domain.pedido.mapper.ItemPedidoMapper;
import br.com.postechfiap.jlapppedido.domain.pedido.model.ItemPedido;
import br.com.postechfiap.jlapppedido.infra.config.db.repository.ItemPedidoRepository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ItemPedidoSchema;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

@Component
public class ItemPedidoGateway implements IItemPedidoGateway {

  private final ItemPedidoRepository itemPedidoRepository;

  private final Logger log;

  public ItemPedidoGateway(ItemPedidoRepository itemPedidoRepository, Logger log) {
    this.itemPedidoRepository = itemPedidoRepository;
    this.log = log;
  }

  @Override
  public List<ItemPedido> inserir(List<ItemPedido> itemPedidos) {

    List<ItemPedidoSchema> itemPedidoSchemas = new ArrayList<>();

    for (ItemPedido itemPedido : itemPedidos) {
      log.info("Cadastrando novo {} na base de dados!", itemPedido);
      itemPedidoSchemas
          .add(itemPedidoRepository.save(ItemPedidoMapper.toItemPedidoSchema(itemPedido)));
    }

    return itemPedidoSchemas.stream().map(ItemPedidoMapper::toDomain).toList();

  }

  @Override
  public List<ItemPedido> buscarItemPedido(Long idPedido) {

    log.info("Buscando item pedido com o ID: {} na base de dados!", idPedido);
    List<ItemPedidoSchema> itemPedidoSchemas = itemPedidoRepository.findAllByPedidoid(idPedido);

    return itemPedidoSchemas.stream().map(ItemPedidoMapper::toDomain).toList();
  }

}
