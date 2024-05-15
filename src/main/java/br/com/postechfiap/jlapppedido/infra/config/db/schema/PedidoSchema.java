package br.com.postechfiap.jlapppedido.infra.config.db.schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import br.com.postechfiap.jlapppedido.domain.enums.Estado;
import br.com.postechfiap.jlapppedido.domain.enums.StatusPagamento;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
@Entity(name = "pedidos")
public class PedidoSchema implements Serializable {

  private static final long serialVersionUID = 106181416585362479L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_pedido")
  private Long id;

  private String numeroPedido;

  @Enumerated
  private StatusPagamento statusPagamento;

  @ManyToOne(optional = true, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
  @JoinColumn(nullable = true, name = "id_cliente")
  private ClienteSchema clienteSchema;

  @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "pedidoSchema")
  private List<ItemPedidoSchema> itensPedidoSchema;

  @Enumerated
  private Estado estado;

  private LocalDateTime dataPedido;

  private BigDecimal valorPedido;

}
