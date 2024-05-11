package br.com.postechfiap.jlapppedido.domain.pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.postechfiap.jlapppedido.domain.produto.dto.ProdutoDTO;
import lombok.Data;

@Data
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


}
