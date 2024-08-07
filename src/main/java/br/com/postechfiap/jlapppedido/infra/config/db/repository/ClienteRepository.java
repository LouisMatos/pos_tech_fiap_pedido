package br.com.postechfiap.jlapppedido.infra.config.db.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.ClienteSchema;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteSchema, Long> {

  Optional<ClienteSchema> findByCpf(String cpf);

  void deleteByCpf(String cpf);

}
