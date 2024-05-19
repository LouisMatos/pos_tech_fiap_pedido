package br.com.postechfiap.jlapppedido.domain.categoria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoriaDTO {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("nome")
  private String nome;

  @JsonProperty("descricao")
  private String descricao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  @Override
  public String toString() {
    return "CategoriaDTO [id=" + id + ", nome=" + nome + ", descricao=" + descricao + "]";
  }


}
