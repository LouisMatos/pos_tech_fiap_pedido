package br.com.postechfiap.jlapppedido.domain.categoria.gateway;

import java.util.Optional;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;

public interface ICategoriaGateway {

  public Optional<Categoria> buscarCategoriaPorId(Long id);

}
