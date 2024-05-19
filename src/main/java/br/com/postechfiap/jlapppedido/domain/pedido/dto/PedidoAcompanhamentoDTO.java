package br.com.postechfiap.jlapppedido.domain.pedido.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.postechfiap.jlapppedido.domain.enums.Estado;


public class PedidoAcompanhamentoDTO {

  @JsonProperty("numero_pedido")
  private String numeroPedido;

  @JsonProperty("estado")
  private Estado estado;

  @JsonProperty("nome_cliente")
  private String nomeCliente;

  @JsonProperty("data_pedido")
  private LocalDateTime dataPedido;

  public String getNumeroPedido() {
    return numeroPedido;
  }

  public void setNumeroPedido(String numeroPedido) {
    this.numeroPedido = numeroPedido;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public String getNomeCliente() {
    return nomeCliente;
  }

  public void setNomeCliente(String nomeCliente) {
    this.nomeCliente = nomeCliente;
  }

  public LocalDateTime getDataPedido() {
    return dataPedido;
  }

  public void setDataPedido(LocalDateTime dataPedido) {
    this.dataPedido = dataPedido;
  }

  @Override
  public String toString() {
    return "PedidoAcompanhamentoDTO [numeroPedido=" + numeroPedido + ", estado=" + estado
        + ", nomeCliente=" + nomeCliente + ", dataPedido=" + dataPedido + "]";
  }


}
