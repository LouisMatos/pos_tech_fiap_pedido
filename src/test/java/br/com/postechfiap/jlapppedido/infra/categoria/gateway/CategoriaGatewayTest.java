package br.com.postechfiap.jlapppedido.infra.categoria.gateway;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.infra.config.db.repository.CategoriaRepository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.CategoriaSchema;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

class CategoriaGatewayTest {

  @InjectMocks
  private CategoriaGateway categoriaGateway;

  @Mock
  private CategoriaRepository categoriaRepository;

  @Mock
  private Logger log;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should return category by id")
  void shouldReturnCategoryById() {
    Long id = 1L;
    when(categoriaRepository.findById(id)).thenReturn(Optional.of(new CategoriaSchema()));

    Optional<Categoria> result = categoriaGateway.buscarCategoriaPorId(id);

    assertTrue(result.isPresent());
  }

  @Test
  @DisplayName("Should return empty when category id not found")
  void shouldReturnEmptyWhenCategoryIdNotFound() {
    Long id = 1L;
    when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

    Optional<Categoria> result = categoriaGateway.buscarCategoriaPorId(id);

    assertFalse(result.isPresent());
  }
}
