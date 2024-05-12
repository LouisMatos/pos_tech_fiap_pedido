package br.com.postechfiap.jlapppedido.infra.config;

import java.util.UUID;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

  @Value("${mq.queues.pedidos}")
  private String pedidoQueue;

  @Bean
  public Queue pedidoQueue() {
    return new Queue(pedidoQueue, true);
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setConsumerTagStrategy(queue -> "totem-pedido-" + UUID.randomUUID());
    return factory;
  }

}
