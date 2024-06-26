package br.com.postechfiap.jlapppedido.domain.pedido.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import br.com.postechfiap.jlapppedido.domain.cliente.model.Cliente;
import br.com.postechfiap.jlapppedido.domain.enums.Estado;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;

public class Pedido {

  private Long id;

  private String numeroPedido;

  private StatusPagamento statusPagamento;

  private Cliente cliente;

  private List<ItemPedido> itens = new ArrayList<>();

  private Estado estado;

  private LocalDateTime dataPedido;

  private BigDecimal valorPedido;

  private boolean enviadoCozinha;

  public Pedido() {

  }

  public Pedido(Long id, String numeroPedido, StatusPagamento statusPagamento, Cliente cliente,
      List<ItemPedido> itens, Estado estado, LocalDateTime dataPedido, BigDecimal valorPedido,
      boolean enviadoCozinha) {
    this.id = id;
    this.numeroPedido = numeroPedido;
    this.statusPagamento = statusPagamento;
    this.cliente = cliente;
    this.itens = itens;
    this.estado = estado;
    this.dataPedido = dataPedido;
    this.valorPedido = valorPedido;
    this.enviadoCozinha = enviadoCozinha;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<ItemPedido> getItens() {
    return itens;
  }

  public void setItens(List<ItemPedido> itens) {
    this.itens = itens;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public boolean isEnviadoCozinha() {
    return enviadoCozinha;
  }

  public void setEnviadoCozinha(boolean enviadoCozinha) {
    this.enviadoCozinha = enviadoCozinha;
  }

  @Override
  public String toString() {
    return "Pedido [id=" + id + ", numeroPedido=" + numeroPedido + ", statusPagamento="
        + statusPagamento + ", cliente=" + cliente + ", itens=" + itens + ", estado=" + estado
        + ", dataPedido=" + dataPedido + ", valorPedido=" + valorPedido + ", enviadoCozinha="
        + enviadoCozinha + "]";
  }

}
