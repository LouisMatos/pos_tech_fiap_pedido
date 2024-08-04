package br.com.postechfiap.jlapppedido.domain.pedido.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import br.com.postechfiap.jlapppedido.domain.cliente.mapper.ClienteMapper;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoAcompanhamentoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.model.EventoPedido;
import br.com.postechfiap.jlapppedido.domain.pedido.model.EventoPedidoCozinha;
import br.com.postechfiap.jlapppedido.domain.pedido.model.Pedido;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.PedidoSchema;

public class PedidoMapper {


  public static PedidoDTO toDTO(Pedido pedido) {
    PedidoDTO pedidoDTO = new PedidoDTO();
    pedidoDTO.setId(pedido.getId());
    if (pedido.getCliente() != null) {
      pedidoDTO.setClienteDTO(ClienteMapper.toDTO(pedido.getCliente()));
    }
    pedidoDTO.setDataPedido(pedido.getDataPedido());
    pedidoDTO.setEstado(pedido.getEstado());
    pedidoDTO.setItemPedidoDTOs(ItemPedidoMapper.toListItemPedidoDTO(pedido.getItens()));
    pedidoDTO.setNumeroPedido(pedido.getNumeroPedido());
    pedidoDTO.setStatusPagamento(pedido.getStatusPagamento());
    pedidoDTO.setValorPedido(pedido.getValorPedido());
    pedidoDTO.setEnviadoCozinha(pedido.isEnviadoCozinha());

    return pedidoDTO;
  }


  public static List<Pedido> toListDomain(List<PedidoSchema> pedidosSchema) {
    if (pedidosSchema == null || pedidosSchema.isEmpty()) {
      return Collections.emptyList();
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
      return Collections.emptyList();
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
      return Collections.emptyList();
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
    pedido.setEnviadoCozinha(pedidoSchema.isEnviadoCozinha());

    return pedido;
  }



  public static EventoPedido toEventoPedido(Pedido pedido) {
    EventoPedido eventoPedido = new EventoPedido();

    eventoPedido.setId(pedido.getId());
    eventoPedido.setNumeroPedido(pedido.getNumeroPedido());
    eventoPedido.setStatusPagamento(pedido.getStatusPagamento());
    eventoPedido.setEstado(pedido.getEstado());
    eventoPedido.setDataPedido(pedido.getDataPedido());
    eventoPedido.setValorPedido(pedido.getValorPedido());


    return eventoPedido;
  }



  public static List<EventoPedidoCozinha> toEventoPedidoCozinha(List<PedidoDTO> pedidoDTOs) {
    if (pedidoDTOs == null || pedidoDTOs.isEmpty()) {
      return Collections.emptyList();
    }

    return pedidoDTOs.stream().map(PedidoMapper::toEventoPedidoCozinha)
        .collect(Collectors.toList());
  }

  public static EventoPedidoCozinha toEventoPedidoCozinha(PedidoDTO pedidoDTO) {
    EventoPedidoCozinha eventoPedidoCozinha = new EventoPedidoCozinha();

    eventoPedidoCozinha.setId(pedidoDTO.getId());
    eventoPedidoCozinha.setNumeroPedido(pedidoDTO.getNumeroPedido());
    eventoPedidoCozinha.setStatusPagamento(pedidoDTO.getStatusPagamento());
    eventoPedidoCozinha.setEstado(pedidoDTO.getEstado());
    eventoPedidoCozinha.setDataPedido(pedidoDTO.getDataPedido());

    eventoPedidoCozinha.setJsonPedido(toJson(pedidoDTO));

    return eventoPedidoCozinha;
  }

  private static String toJson(PedidoDTO pedidoDTO) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    String json = "";
    try {
      json = objectMapper.writeValueAsString(pedidoDTO);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return json;
  }

  public static PedidoAcompanhamentoDTO toPedidoAcompanhamento(PedidoDTO pedidoDTO) {
    PedidoAcompanhamentoDTO pedidoAcompanhamentoDTO = new PedidoAcompanhamentoDTO();
    pedidoAcompanhamentoDTO.setNumeroPedido(pedidoDTO.getNumeroPedido());
    pedidoAcompanhamentoDTO.setEstado(pedidoDTO.getEstado());
    pedidoAcompanhamentoDTO.setNomeCliente(pedidoDTO.getClienteDTO().getNome());
    pedidoAcompanhamentoDTO.setDataPedido(pedidoDTO.getDataPedido());
    return pedidoAcompanhamentoDTO;
  }

}
