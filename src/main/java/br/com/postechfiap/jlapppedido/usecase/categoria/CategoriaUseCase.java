package br.com.postechfiap.jlapppedido.usecase.categoria;

import br.com.postechfiap.jlapppedido.domain.categoria.dto.CategoriaDTO;
import br.com.postechfiap.jlapppedido.domain.categoria.gateway.ICategoriaGateway;
import br.com.postechfiap.jlapppedido.domain.categoria.mapper.CategoriaMapper;
import br.com.postechfiap.jlapppedido.shared.exception.NotFoundException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

public class CategoriaUseCase {

  private final ICategoriaGateway categoriaGateway;

  private final Logger log;

  public CategoriaUseCase(ICategoriaGateway categoriaGateway, Logger log) {
    this.categoriaGateway = categoriaGateway;
    this.log = log;
  }

  public CategoriaDTO buscarCategoriaPorId(Long id) {
    log.info("Verificando se a categoria está cadastrada na base");
    return CategoriaMapper.toDTO(categoriaGateway.buscarCategoriaPorId(id)
        .orElseThrow(() -> new NotFoundException("Categoria informado não encontrado!")));
  }

}
