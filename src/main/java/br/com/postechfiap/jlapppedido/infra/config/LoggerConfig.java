package br.com.postechfiap.jlapppedido.infra.config;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import br.com.postechfiap.jlapppedido.shared.logger.Slf4jLogger;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

@Configuration
public class LoggerConfig {

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public Logger logger(final InjectionPoint ip) {
    return new Slf4jLogger(ip);
  }

}
