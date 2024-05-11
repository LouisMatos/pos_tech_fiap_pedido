package br.com.postechfiap.jlapppedido.infra.config.db.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ProdutoSchema;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoSchema, Long> {

  List<ProdutoSchema> findCategoriaEntityById(Long id);

}
