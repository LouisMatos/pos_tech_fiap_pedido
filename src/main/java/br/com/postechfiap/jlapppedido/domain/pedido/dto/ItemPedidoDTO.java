package br.com.postechfiap.jlapppedido.domain.pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.postechfiap.jlapppedido.domain.produto.dto.ProdutoDTO;

public class ItemPedidoDTO {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("pedido")
  private Long pedidoid;

  @JsonProperty("produto")
  private ProdutoDTO produtoDTO;

  @JsonProperty("quantidade")
  private int quantidade;

  @JsonProperty("observacao")
  private String observacao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPedidoid() {
    return pedidoid;
  }

  public void setPedidoid(Long pedidoid) {
    this.pedidoid = pedidoid;
  }

  public ProdutoDTO getProdutoDTO() {
    return produtoDTO;
  }

  public void setProdutoDTO(ProdutoDTO produtoDTO) {
    this.produtoDTO = produtoDTO;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

}
