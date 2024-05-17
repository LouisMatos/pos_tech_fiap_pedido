package br.com.postechfiap.jlapppedido.usecase.categoria;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.postechfiap.jlapppedido.domain.categoria.dto.CategoriaDTO;
import br.com.postechfiap.jlapppedido.domain.categoria.gateway.ICategoriaGateway;
import br.com.postechfiap.jlapppedido.domain.categoria.model.Categoria;
import br.com.postechfiap.jlapppedido.shared.exception.NotFoundException;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

class CategoriaUseCaseTest {

  @InjectMocks
  private CategoriaUseCase categoriaUseCase;

  @Mock
  private ICategoriaGateway categoriaGateway;

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
    when(categoriaGateway.buscarCategoriaPorId(id)).thenReturn(Optional.of(new Categoria()));

    CategoriaDTO result = categoriaUseCase.buscarCategoriaPorId(id);

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should throw exception when category id not found")
  void shouldThrowExceptionWhenCategoryIdNotFound() {
    Long id = 1L;
    when(categoriaGateway.buscarCategoriaPorId(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> categoriaUseCase.buscarCategoriaPorId(id));
  }
}
