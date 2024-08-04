package br.com.postechfiap.jlapppedido.domain.enums;

public enum StatusPagamento {

  AGUARDANDO(1), //
  APROVADO(2), //
  NEGADO(3); //

  private int nomeStatusPagamento;

  StatusPagamento(int statusPagamento) {
    this.nomeStatusPagamento = statusPagamento;
  }

  public boolean estaAguardando() {
    return AGUARDANDO.getValorEstado() == this.nomeStatusPagamento;
  }

  public boolean estaAprovado() {
    return APROVADO.getValorEstado() == this.nomeStatusPagamento;
  }

  public boolean estaNegado() {
    return NEGADO.getValorEstado() == this.nomeStatusPagamento;
  }

  public int getValorEstado() {
    return this.nomeStatusPagamento;
  }

}
