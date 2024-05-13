package br.com.postechfiap.jlapppedido;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRabbit
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"br.com.postechfiap.jlapppedido"})
@EnableJpaRepositories(basePackages = "br.com.postechfiap.jlapppedido.infra.config.db.repository")
public class JlappPedidoApplication {

  public static void main(String[] args) {
    SpringApplication.run(JlappPedidoApplication.class, args);
  }

}
