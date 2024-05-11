package br.com.postechfiap.jlapppedido.infra.config.db.schema;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "clientes")
public class ClienteSchema implements Serializable {

  private static final long serialVersionUID = -6464967453767426458L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_cliente")
  private Long id;

  @OneToMany(mappedBy = "clienteSchema", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
  @Column(nullable = true)
  private List<PedidoSchema> pedidosSchema;

  private String nome;

  private String cpf;

  private String email;

}
