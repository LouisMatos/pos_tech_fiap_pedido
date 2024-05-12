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

  @Value("${mq.exchanges.direct}")
  private String directExchange;

  @Value("${mq.routing.key.pedidos}")
  private String pedidoRoutingKey;

  @Value("${mq.routing.key.statuspedidos}")
  private String statusPedidoRoutingKey;

  @Bean
  public Queue pedidoQueue() {
    return new Queue(pedidoQueue, true);
  }

  @Bean
  public Queue statusPedidoQueue() {
    return new Queue(statusPedidoQueue, true);
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

}
