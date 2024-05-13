package br.com.postechfiap.jlapppedido.infra.pedido.gateway;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.postechfiap.jlapppedido.domain.pedido.gateway.IPedidoGateway;
import br.com.postechfiap.jlapppedido.domain.pedido.mapper.PedidoMapper;
import br.com.postechfiap.jlapppedido.domain.pedido.model.Pedido;
import br.com.postechfiap.jlapppedido.infra.config.db.repository.PedidoRepository;
import br.com.postechfiap.jlapppedido.infra.config.db.schema.PedidoSchema;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

@Component
public class PedidoGateway implements IPedidoGateway {

  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private Logger log;

  @Override
  public Pedido inserir(Pedido pedido) {
    PedidoSchema pedidoSchema = pedidoRepository.save(PedidoMapper.toPedidoSchema(pedido));
    log.info("Cadastrando novo pedido na base de dados!");
    return PedidoMapper.toDomain(pedidoSchema);
  }

  @Override
  public List<Pedido> buscarTodos() {
    log.info("Buscando todos os pedidos cadastrados na base de dados!");
    List<PedidoSchema> pedidoSchemas = pedidoRepository.findAll();
    return pedidoSchemas.stream().map(schema -> PedidoMapper.toDomain(schema)).toList();
  }

  @Override
  public Optional<Pedido> buscarStatusPagamentoPedido(String numero_pedido) {
    log.info("Buscando o pedido: {} na base de dados!", numero_pedido);
    Optional<PedidoSchema> pedidoSchema = pedidoRepository.findByNumeroPedido(numero_pedido);
    return pedidoSchema.map(schema -> PedidoMapper.toDomain(schema));
  }

  @Override
  public Optional<Pedido> buscaPedidoNumeroPedido(String numero_pedido) {
    log.info("Buscando o pedido: {} na base de dados!", numero_pedido);
    Optional<PedidoSchema> pedidoSchema = pedidoRepository.findByNumeroPedido(numero_pedido);
    return pedidoSchema.map(schema -> PedidoMapper.toDomain(schema));
  }

  @Override
  public Pedido atualizar(Pedido pedido) {
    PedidoSchema pedidoSchema = PedidoMapper.toPedidoSchema(pedido);
    log.info("Alterando pedido na base de dados!");
    return PedidoMapper.toDomain(pedidoRepository.save(pedidoSchema));
  }

  @Override
  public void atualizarEnviadoCozinha(Long id, boolean enviadoCozinha) {
    log.info("Atualizando pedido enviado para cozinha: {}!", id);
    Optional<PedidoSchema> pedidoSchema = pedidoRepository.findById(id);

    pedidoSchema.get().setEnviadoCozinha(enviadoCozinha);

    pedidoRepository.save(pedidoSchema.get());

  }

}
