package br.com.postechfiap.jlapppedido.domain.pedido.mapper;

import java.util.List;
import java.util.stream.Collectors;
import br.com.postechfiap.jlapppedido.domain.cliente.mapper.ClienteMapper;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.model.Pedido;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.PedidoSchema;

public class PedidoMapper {

  public static PedidoDTO toDTO(Pedido pedido) {

    return PedidoDTO.builder().build();
  }

  public static List<Pedido> toListDomain(List<PedidoSchema> pedidosSchema) {
    if (pedidosSchema == null || pedidosSchema.isEmpty()) {
      return null;
    }

    return pedidosSchema.stream().map(pedidoSchema -> {
      Pedido pedido = new Pedido();

      pedido.setId(pedidoSchema.getId());
      if (pedido.getCliente() != null) {
        pedido.setCliente(ClienteMapper.toDomain(pedidoSchema.getClienteSchema()));
      }
      pedido.setDataPedido(pedidoSchema.getDataPedido());
      pedido.setEstado(pedidoSchema.getEstado());
      pedido.setItens(ItemPedidoMapper.toListDomain(pedidoSchema.getItensPedidoSchema()));
      pedido.setNumeroPedido(pedidoSchema.getNumeroPedido());
      pedido.setStatusPagamento(pedidoSchema.getStatusPagamento());
      pedido.setValorPedido(pedidoSchema.getValorPedido());

      return pedido;
    }).collect(Collectors.toList());

  }

  public static List<PedidoSchema> toListPedidoSchema(List<Pedido> pedidos) {
    if (pedidos == null || pedidos.isEmpty()) {
      return null;
    }

    return pedidos.stream().map(pedido -> {
      PedidoSchema pedidoSchema = new PedidoSchema();

      pedidoSchema.setId(pedido.getId());
      pedidoSchema.setClienteSchema(ClienteMapper.toClienteSchema(pedido.getCliente()));
      pedidoSchema.setDataPedido(pedido.getDataPedido());
      pedidoSchema.setEstado(pedido.getEstado());
      pedidoSchema
          .setItensPedidoSchema(ItemPedidoMapper.toListItensPedidoSchema(pedido.getItens()));
      pedidoSchema.setNumeroPedido(pedido.getNumeroPedido());
      pedidoSchema.setStatusPagamento(pedido.getStatusPagamento());
      pedidoSchema.setValorPedido(pedido.getValorPedido());

      return pedidoSchema;
    }).collect(Collectors.toList());
  }

  public static List<PedidoDTO> toListPedidoDTO(List<Pedido> pedidos) {
    if (pedidos == null || pedidos.isEmpty()) {
      return null;
    }

    return pedidos.stream().map(PedidoMapper::toDTO).collect(Collectors.toList());
  }


  public static Pedido toDomain(PedidoDTO pedidoDTO) {
    Pedido pedido = new Pedido();
    pedido.setId(pedidoDTO.getId());
    if (pedidoDTO.getClienteDTO() != null) {
      pedido.setCliente(ClienteMapper.toDomain(pedidoDTO.getClienteDTO()));
    }
    pedido.setDataPedido(pedidoDTO.getDataPedido());
    pedido.setEstado(pedidoDTO.getEstado());


    pedido.setItens(pedidoDTO.getItemPedidoDTOs().stream().map(ItemPedidoMapper::toDomain)
        .collect(Collectors.toList()));

    pedido.setNumeroPedido(pedidoDTO.getNumeroPedido());
    pedido.setStatusPagamento(pedidoDTO.getStatusPagamento());
    pedido.setValorPedido(pedidoDTO.getValorPedido());

    return pedido;
  }


  public static PedidoSchema toPedidoSchema(Pedido pedido) {
    PedidoSchema pedidoSchema = new PedidoSchema();
    pedidoSchema.setId(pedido.getId());
    pedidoSchema.setClienteSchema(ClienteMapper.toClienteSchema(pedido.getCliente()));
    pedidoSchema.setDataPedido(pedido.getDataPedido());
    pedidoSchema.setEstado(pedido.getEstado());
    pedidoSchema.setItensPedidoSchema(ItemPedidoMapper.toListItensPedidoSchema(pedido.getItens()));
    pedidoSchema.setNumeroPedido(pedido.getNumeroPedido());
    pedidoSchema.setStatusPagamento(pedido.getStatusPagamento());
    pedidoSchema.setValorPedido(pedido.getValorPedido());

    return pedidoSchema;
  }


  public static Pedido toDomain(PedidoSchema pedidoSchema) {
    Pedido pedido = new Pedido();
    pedido.setId(pedidoSchema.getId());
    pedido.setCliente(ClienteMapper.toDomain(pedidoSchema.getClienteSchema()));
    pedido.setDataPedido(pedidoSchema.getDataPedido());
    pedido.setEstado(pedidoSchema.getEstado());
    pedido.setItens(ItemPedidoMapper.toListDomain(pedidoSchema.getItensPedidoSchema()));
    pedido.setNumeroPedido(pedidoSchema.getNumeroPedido());
    pedido.setStatusPagamento(pedidoSchema.getStatusPagamento());
    pedido.setValorPedido(pedidoSchema.getValorPedido());

    return pedido;
  }

}
