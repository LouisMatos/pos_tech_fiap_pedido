package br.com.postechfiap.jlapppedido.domain.pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusPedidoDTO {

  @JsonProperty("numero_pedido")
  private String numeroPedido;

  @JsonProperty("status_pagamento")
  private StatusPagamento statusPagamento;

}
