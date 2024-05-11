package br.com.postechfiap.jlapppedido.domain.categoria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoriaDTO {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("nome")
  private String nome;

  @JsonProperty("descricao")
  private String descricao;

}
