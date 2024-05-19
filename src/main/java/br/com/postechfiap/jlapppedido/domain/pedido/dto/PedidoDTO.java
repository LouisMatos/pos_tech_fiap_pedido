package br.com.postechfiap.jlapppedido.domain.pedido.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteDTO;
import br.com.postechfiap.jlapppedido.domain.enums.Estado;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;


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

  @JsonProperty("itens")
  private List<ItemPedidoDTO> itemPedidoDTOs = new ArrayList<>();

  private boolean enviadoCozinha;

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

  public BigDecimal getValorPedido() {
    return valorPedido;
  }

  public void setValorPedido(BigDecimal valorPedido) {
    this.valorPedido = valorPedido;
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

  public ClienteDTO getClienteDTO() {
    return clienteDTO;
  }

  public void setClienteDTO(ClienteDTO clienteDTO) {
    this.clienteDTO = clienteDTO;
  }

  public List<ItemPedidoDTO> getItemPedidoDTOs() {
    return itemPedidoDTOs;
  }

  public void setItemPedidoDTOs(List<ItemPedidoDTO> itemPedidoDTOs) {
    this.itemPedidoDTOs = itemPedidoDTOs;
  }

  public boolean isEnviadoCozinha() {
    return enviadoCozinha;
  }

  public void setEnviadoCozinha(boolean enviadoCozinha) {
    this.enviadoCozinha = enviadoCozinha;
  }

  @Override
  public String toString() {
    return "PedidoDTO [id=" + id + ", numeroPedido=" + numeroPedido + ", valorPedido=" + valorPedido
        + ", statusPagamento=" + statusPagamento + ", estado=" + estado + ", dataPedido="
        + dataPedido + ", clienteDTO=" + clienteDTO + ", itemPedidoDTOs=" + itemPedidoDTOs
        + ", enviadoCozinha=" + enviadoCozinha + "]";
  }


}
