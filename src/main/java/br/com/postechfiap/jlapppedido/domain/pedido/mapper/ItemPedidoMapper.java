package br.com.postechfiap.jlapppedido.domain.pedido.mapper;

import java.util.ArrayList;
import java.util.List;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.ItemPedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.model.ItemPedido;
import br.com.postechfiap.jlapppedido.domain.produto.mapper.ProdutoMapper;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ItemPedidoSchema;

public class ItemPedidoMapper {

  public static List<ItemPedido> toListDomain(List<ItemPedidoSchema> itensPedidoSchema) {
    List<ItemPedido> itemPedidoList = new ArrayList<>();
    for (ItemPedidoSchema schema : itensPedidoSchema) {
      ItemPedido itemPedido = new ItemPedido();
      itemPedido.setId(schema.getId());
      itemPedido.setPedidoid(schema.getPedidoid());
      itemPedido.setProduto(ProdutoMapper.toDomain(schema.getProdutoSchema()));
      itemPedido.setQuantidade(schema.getQuantidade());
      itemPedido.setObservacao(schema.getObservacao());
    }
    return itemPedidoList;
  }

  public static List<ItemPedidoSchema> toListItensPedidoSchema(List<ItemPedido> itens) {
    List<ItemPedidoSchema> itemPedidoSchemaList = new ArrayList<>();
    for (ItemPedido item : itens) {
      ItemPedidoSchema itemPedidoSchema = new ItemPedidoSchema();
      itemPedidoSchema.setId(item.getId());
      itemPedidoSchema.setPedidoid(item.getPedidoid());
      itemPedidoSchema.setProdutoSchema(ProdutoMapper.toProdutoSchema(item.getProduto()));
      itemPedidoSchema.setQuantidade(item.getQuantidade());
      itemPedidoSchema.setObservacao(item.getObservacao());
      itemPedidoSchemaList.add(itemPedidoSchema);
    }
    return itemPedidoSchemaList;
  }


  public static ItemPedido toDomain(ItemPedidoDTO itemPedidoDTO) {
    ItemPedido itemPedido = new ItemPedido();
    itemPedido.setId(itemPedidoDTO.getId());
    itemPedido.setPedidoid(itemPedidoDTO.getPedidoid());
    itemPedido.setProduto(ProdutoMapper.toDomain(itemPedidoDTO.getProdutoDTO()));
    itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
    itemPedido.setObservacao(itemPedidoDTO.getObservacao());

    return itemPedido;
  }

  public static ItemPedido toDomain(ItemPedidoSchema itemPedidoSchema) {
    ItemPedido itemPedido = new ItemPedido();
    itemPedido.setId(itemPedidoSchema.getId());
    itemPedido.setPedidoid(itemPedidoSchema.getPedidoid());
    itemPedido.setProduto(ProdutoMapper.toDomain(itemPedidoSchema.getProdutoSchema()));
    itemPedido.setQuantidade(itemPedidoSchema.getQuantidade());
    itemPedido.setObservacao(itemPedidoSchema.getObservacao());

    return itemPedido;
  }


  public static ItemPedidoDTO toDTO(ItemPedido itemPedido) {
    ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
    itemPedidoDTO.setId(itemPedido.getId());
    itemPedidoDTO.setPedidoid(itemPedido.getPedidoid());
    itemPedidoDTO.setProdutoDTO(ProdutoMapper.toDTO(itemPedido.getProduto()));
    itemPedidoDTO.setQuantidade(itemPedido.getQuantidade());
    itemPedidoDTO.setObservacao(itemPedido.getObservacao());

    return itemPedidoDTO;
  }

  public static ItemPedidoSchema toItemPedidoSchema(ItemPedido itemPedido) {
    ItemPedidoSchema itemPedidoSchema = new ItemPedidoSchema();
    itemPedidoSchema.setId(itemPedido.getId());
    itemPedidoSchema.setPedidoid(itemPedido.getPedidoid());
    itemPedidoSchema.setProdutoSchema(ProdutoMapper.toProdutoSchema(itemPedido.getProduto()));
    itemPedidoSchema.setQuantidade(itemPedido.getQuantidade());
    itemPedidoSchema.setObservacao(itemPedido.getObservacao());

    return itemPedidoSchema;
  }

}
