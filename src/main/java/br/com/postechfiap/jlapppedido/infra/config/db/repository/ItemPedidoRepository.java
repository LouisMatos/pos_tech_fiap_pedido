package br.com.postechfiap.jlapppedido.infra.config.db.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ItemPedidoSchema;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedidoSchema, Long> {

  List<ItemPedidoSchema> findAllByPedidoid(Long idPedido);

}
