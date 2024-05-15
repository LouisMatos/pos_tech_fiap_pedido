package br.com.postechfiap.jlapppedido.infra.config.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import br.com.postechfiap.jlapppedido.domain.pedido.model.EventoPedido;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.usecase.pedido.PedidoUseCase;

@Component
public class StatusPagamentoPedidosSubscriber {

  private final PedidoUseCase peddidoUseCase;

  private final Logger log;

  public StatusPagamentoPedidosSubscriber(Logger log, PedidoUseCase peddidoUseCase) {
    this.peddidoUseCase = peddidoUseCase;
    this.log = log;
  }

  @RabbitListener(queues = "${mq.queues.statuspedidos}")
  public void receive(String message) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {

      EventoPedido pedido = mapper.readValue(message, EventoPedido.class);
      log.info("Pedido recebido da fila: {}", pedido);
      peddidoUseCase.atualizarStatusPagamento(pedido.getNumeroPedido(), pedido.getStatusPagamento(),
          pedido.getEstado());

    } catch (Exception e) {
      log.error("Erro ao processar a mensagem: {}", message, e);
    }
  }

}
