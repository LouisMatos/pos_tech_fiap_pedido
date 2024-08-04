package br.com.postechfiap.jlapppedido.domain.pedido.gateway;

import java.util.List;
import java.util.Optional;
import br.com.postechfiap.jlapppedido.domain.pedido.model.Pedido;

public interface IPedidoGateway {

  public Pedido inserir(Pedido pedido);

  public List<Pedido> buscarTodos();

  public Optional<Pedido> buscarStatusPagamentoPedido(String numeroPedido);

  public Optional<Pedido> buscaPedidoNumeroPedido(String numeroPedido);

  public Pedido atualizar(Pedido pedido);

  void atualizarEnviadoCozinha(Long id, boolean enviadoCozinha);

}
