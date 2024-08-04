package br.com.postechfiap.jlapppedido.infra.categoria.gateway;

import java.util.Optional;
import org.springframework.stereotype.Component;
import br.com.postechfiap.jlapppedido.domain.categoria.gateway.ICategoriaGateway;
import br.com.postechfiap.jlapppedido.domain.categoria.mapper.CategoriaMapper;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.infra.config.db.repository.CategoriaRepository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.CategoriaSchema;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

@Component
public class CategoriaGateway implements ICategoriaGateway {

  private final CategoriaRepository categoriaRepository;

  private final Logger log;

  public CategoriaGateway(CategoriaRepository categoriaRepository, Logger log) {
    this.categoriaRepository = categoriaRepository;
    this.log = log;
  }

  @Override
  public Optional<Categoria> buscarCategoriaPorId(Long id) {
    log.info("Buscando categoria com id - {} na base de dados!", id);
    Optional<CategoriaSchema> categoriaSchema = categoriaRepository.findById(id);
    return categoriaSchema.map(CategoriaMapper::toDomain);
  }

}
