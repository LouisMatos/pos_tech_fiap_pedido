package br.com.postechfiap.jlapppedido.domain.produto.dto;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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

}
