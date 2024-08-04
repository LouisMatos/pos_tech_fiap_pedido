package br.com.postechfiap.jlapppedido.infra.config.db.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.PedidoSchema;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoSchema, Long> {

  Optional<PedidoSchema> findByNumeroPedido(String numeroPedido);

}
