package br.com.postechfiap.jlapppedido.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

  @Value("${mq.queues.pedidos}")
  private String pedidoQueue;

  @Value("${mq.queues.statuspedidos}")
  private String statusPedidoQueue;

  @Value("${mq.queues.cozinha}")
  private String cozinhaQueue;

  @Value("${mq.exchanges.direct}")
  private String directExchange;

  @Value("${mq.routing.key.pedidos}")
  private String pedidoRoutingKey;

  @Value("${mq.routing.key.statuspedidos}")
  private String statusPedidoRoutingKey;

  @Value("${mq.routing.key.cozinha}")
  private String cozinhaRoutingKey;

  @Bean
  public Queue pedidoQueue() {
    return new Queue(pedidoQueue, true);
  }

  @Bean
  public Queue statusPedidoQueue() {
    return new Queue(statusPedidoQueue, true);
  }

  @Bean
  public Queue cozinhaQueue() {
    return new Queue(cozinhaQueue, true);
  }

  @Bean
  DirectExchange exchange() {
    return new DirectExchange(directExchange);
  }

  @Bean
  Binding pedidoBinding(Queue pedidoQueue, DirectExchange exchange) {
    return BindingBuilder.bind(pedidoQueue).to(exchange).with(pedidoRoutingKey);
  }

  @Bean
  Binding statusPedidoBinding(Queue statusPedidoQueue, DirectExchange exchange) {
    return BindingBuilder.bind(statusPedidoQueue).to(exchange).with(statusPedidoRoutingKey);
  }

  @Bean
  Binding cozinhaBinding(Queue cozinhaQueue, DirectExchange exchange) {
    return BindingBuilder.bind(cozinhaQueue).to(exchange).with(cozinhaRoutingKey);
  }

}
