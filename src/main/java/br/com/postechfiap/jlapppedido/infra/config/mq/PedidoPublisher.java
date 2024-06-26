package br.com.postechfiap.jlapppedido.infra.config.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import br.com.postechfiap.jlapppedido.domain.pedido.model.EventoPedido;
import br.com.postechfiap.jlapppedido.domain.pedido.model.EventoPedidoCozinha;
import br.com.postechfiap.jlapppedido.domain.pedido.model.Pedido;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoPublisher {

  private final RabbitTemplate rabbitTemplate;
  private final Queue pedidoQueue;
  private final Queue cozinhaQueue;


  public void send(Pedido pedido) throws JsonProcessingException {
    String json = convertIntoJson(pedido);
    rabbitTemplate.convertAndSend(pedidoQueue.getName(), json);
  }

  public void send(EventoPedido eventoPedido) throws JsonProcessingException {
    String json = convertIntoJson(eventoPedido);
    rabbitTemplate.convertAndSend(pedidoQueue.getName(), json);
  }

  public void sendPedidoCozinha(EventoPedidoCozinha eventoPedidoCozinha)
      throws JsonProcessingException {
    String json = convertIntoJson(eventoPedidoCozinha);
    rabbitTemplate.convertAndSend(cozinhaQueue.getName(), json);
  }

  private String convertIntoJson(Pedido pedido) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper.writeValueAsString(pedido);
  }

  private String convertIntoJson(EventoPedido eventoPedido) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper.writeValueAsString(eventoPedido);
  }

  private String convertIntoJson(EventoPedidoCozinha eventoPedidoCozinha)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper.writeValueAsString(eventoPedidoCozinha);
  }

}
