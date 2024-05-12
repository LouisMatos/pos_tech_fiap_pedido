package br.com.postechfiap.jlapppedido.domain.pedido.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import br.com.postechfiap.jlapppedido.domain.enums.Estado;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;

public class EventoPedido {

  private Long id;

  private String numeroPedido;

  private StatusPagamento statusPagamento;

  private Estado estado;

  private LocalDateTime dataPedido;

  private BigDecimal valorPedido;

  public EventoPedido() {

  }

  public EventoPedido(Long id, String numeroPedido, StatusPagamento statusPagamento, Estado estado,
      LocalDateTime dataPedido, BigDecimal valorPedido) {
    this.id = id;
    this.numeroPedido = numeroPedido;
    this.statusPagamento = statusPagamento;
    this.estado = estado;
    this.dataPedido = dataPedido;
    this.valorPedido = valorPedido;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public LocalDateTime getDataPedido() {
    return dataPedido;
  }

  public void setDataPedido(LocalDateTime dataPedido) {
    this.dataPedido = dataPedido;
  }

  public BigDecimal getValorPedido() {
    return valorPedido;
  }

  public void setValorPedido(BigDecimal valorPedido) {
    this.valorPedido = valorPedido;
  }

  @Override
  public String toString() {
    return "EventoPedido [id=" + id + ", numeroPedido=" + numeroPedido + ", statusPagamento="
        + statusPagamento + ", estado=" + estado + ", dataPedido=" + dataPedido + ", valorPedido="
        + valorPedido + "]";
  }

}
