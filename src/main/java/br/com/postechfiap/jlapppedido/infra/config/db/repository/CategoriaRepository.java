package br.com.postechfiap.jlapppedido.infra.config.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.CategoriaSchema;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaSchema, Long> {

}
