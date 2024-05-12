package br.com.postechfiap.jlapppedido.infra.config.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import br.com.postechfiap.jlapppedido.domain.pedido.model.Pedido;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

@Component
public class StatusPagamentoPedidosSubscriber {

  private final Logger log;

  public StatusPagamentoPedidosSubscriber(Logger log) {
    this.log = log;
  }

  @RabbitListener(queues = "${mq.queues.statuspedidos}")
  public void receive(String message) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    try {
      Pedido pedido = mapper.readValue(message, Pedido.class);
      log.info("Pedido recebido: {}", pedido);
    } catch (Exception e) {
      log.error("Erro ao processar a mensagem: {}", message, e);
    }
  }

}
