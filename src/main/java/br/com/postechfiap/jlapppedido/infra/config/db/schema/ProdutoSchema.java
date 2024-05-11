package br.com.postechfiap.jlapppedido.infra.config.db.schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "produtos")
public class ProdutoSchema implements Serializable {

  private static final long serialVersionUID = -6484803166859187193L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_produto")
  private Long id;

  private String nome;

  private String descricao;

  private BigDecimal preco;

  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
  @JoinColumn(name = "categoria_id")
  private CategoriaSchema categoriaSchema;

  @OneToMany(mappedBy = "produtoSchema")
  private List<ItemPedidoSchema> itemPedidoEntity;

  private List<String> imagens;

}
