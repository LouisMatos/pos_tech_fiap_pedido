package br.com.postechfiap.jlapppedido.domain.pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;


public class StatusPedidoDTO {

  @JsonProperty("numero_pedido")
  private String numeroPedido;

  @JsonProperty("status_pagamento")
  private StatusPagamento statusPagamento;

  public StatusPedidoDTO() {}

  public StatusPedidoDTO(String numeroPedido, StatusPagamento statusPagamento) {
    this.numeroPedido = numeroPedido;
    this.statusPagamento = statusPagamento;
  }

  public String getNumeroPedido() {
    return numeroPedido;
  }

  public void setNumeroPedido(String numeroPedido) {
    this.numeroPedido = numeroPedido;
  }

  public StatusPagamento getStatusPagamento() {
    return statusPagamento;
  }

  public void setStatusPagamento(StatusPagamento statusPagamento) {
    this.statusPagamento = statusPagamento;
  }

  @Override
  public String toString() {
    return "StatusPedidoDTO [numeroPedido=" + numeroPedido + ", statusPagamento=" + statusPagamento
        + "]";
  }

}
