package br.com.postechfiap.jlapppedido.domain.enums;

public enum Estado {

  RECEBIDO(1), //
  EM_PREPARACAO(2), //
  PRONTO(3), //
  FINALIZADO(4); //

  private int nomeEstado;

  Estado(int estado) {
    this.nomeEstado = estado;
  }

  public boolean foiRecebido() {
    return RECEBIDO.getValorEstado() == this.nomeEstado;
  }

  public boolean estaEmPreparacao() {
    return EM_PREPARACAO.getValorEstado() == this.nomeEstado;
  }

  public boolean estaPronto() {
    return PRONTO.getValorEstado() == this.nomeEstado;
  }

  public boolean estaFinalizado() {
    return FINALIZADO.getValorEstado() == this.nomeEstado;
  }

  public int getValorEstado() {
    return this.nomeEstado;
  }

}
