package br.com.postechfiap.jlapppedido.domain.pedido.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteDTO;
import br.com.postechfiap.jlapppedido.domain.enums.Estado;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class PedidoDTO {

  private Long id;

  @JsonProperty("numero_pedido")
  private String numeroPedido;

  @JsonProperty("valor_pedido")
  private BigDecimal valorPedido;

  @JsonProperty("status_pagamento")
  private StatusPagamento statusPagamento;

  @JsonProperty("estado")
  private Estado estado;

  @JsonProperty("data_pedido")
  private LocalDateTime dataPedido;

  @JsonProperty("cliente")
  private ClienteDTO clienteDTO;

  @Default
  @JsonProperty("itens")
  private List<ItemPedidoDTO> itemPedidoDTOs = new ArrayList<>();

}
