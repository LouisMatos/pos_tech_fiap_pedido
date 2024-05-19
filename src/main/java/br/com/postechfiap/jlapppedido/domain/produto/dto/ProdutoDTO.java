package br.com.postechfiap.jlapppedido.domain.produto.dto;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoDTO {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("nome")
  private String nome;

  @JsonProperty("descricao")
  private String descricao;

  @JsonProperty("preco")
  private BigDecimal preco;

  @JsonProperty("categoria")
  @JsonInclude(Include.NON_NULL)
  private Long categoriaId;

  @JsonProperty("categoria_nome")
  private String categoriaNome;

  @JsonProperty("imagens")
  private List<String> imagens;

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

  public BigDecimal getPreco() {
    return preco;
  }

  public void setPreco(BigDecimal preco) {
    this.preco = preco;
  }

  public Long getCategoriaId() {
    return categoriaId;
  }

  public void setCategoriaId(Long categoriaId) {
    this.categoriaId = categoriaId;
  }

  public String getCategoriaNome() {
    return categoriaNome;
  }

  public void setCategoriaNome(String categoriaNome) {
    this.categoriaNome = categoriaNome;
  }

  public List<String> getImagens() {
    return imagens;
  }

  public void setImagens(List<String> imagens) {
    this.imagens = imagens;
  }

  @Override
  public String toString() {
    return "ProdutoDTO [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", preco="
        + preco + ", categoriaId=" + categoriaId + ", categoriaNome=" + categoriaNome + ", imagens="
        + imagens + "]";
  }

}
