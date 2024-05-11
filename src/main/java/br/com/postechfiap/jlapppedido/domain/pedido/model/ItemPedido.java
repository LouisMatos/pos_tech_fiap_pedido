package br.com.postechfiap.jlapppedido.domain.pedido.model;

import br.com.postechfiap.jlapppedido.domain.produto.model.Produto;

public class ItemPedido {

  private Long id;

  private Long pedidoid;

  private Produto produto;

  private int quantidade;

  private String observacao;

  public ItemPedido() {

  }

  public ItemPedido(Long id, Long pedidoid, Produto produto, int quantidade, String observacao) {
    this.id = id;
    this.pedidoid = pedidoid;
    this.produto = produto;
    this.quantidade = quantidade;
    this.observacao = observacao;
  }

  public Produto getProduto() {
    return produto;
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
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

  public Long getPedidoid() {
    return pedidoid;
  }

  public void setPedidoid(Long pedidoid) {
    this.pedidoid = pedidoid;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "ItemPedido [id=" + id + ", pedidoid=" + pedidoid + ", produto=" + produto
        + ", quantidade=" + quantidade + ", observacao=" + observacao + "]";
  }

}
