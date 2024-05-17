package br.com.postechfiap.jlapppedido.shared.logger;

import java.lang.reflect.Method;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.core.MethodParameter;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;

public final class Slf4jLogger implements Logger {

  public final org.slf4j.Logger logger;

  public Slf4jLogger(final InjectionPoint ip) {
    this.logger = LoggerFactory.getLogger(getClassName(ip));
  }

  public String getClassName(final InjectionPoint ip) {
    return Optional.ofNullable(getMethodOrElseNull(ip.getMethodParameter()))
        .map(m -> m.getReturnType().getName())
        .orElseGet(() -> getMethodParameterOfDeclaredClass(ip));
  }

  public Method getMethodOrElseNull(final MethodParameter methodParameter) {
    try {
      return methodParameter.getMethod();
    } catch (Exception e) {
      return null;
    }
  }

  public String getMethodParameterOfDeclaredClass(final InjectionPoint ip) {
    return Optional.ofNullable(ip.getMethodParameter()).map(mp -> mp.getDeclaringClass().getName())
        .orElseGet(() -> getFieldParameterOfDeclaredClass(ip));
  }

  public String getFieldParameterOfDeclaredClass(final InjectionPoint ip) {
    return Optional.ofNullable(ip.getField()).map(f -> f.getDeclaringClass().getName())
        .orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public void info(final String format, final Object... arguments) {
    logger.info(format, arguments);
  }

  @Override
  public void warn(final String format, final Object... arguments) {
    logger.warn(format, arguments);
  }

  @Override
  public void error(final String format, final Object... arguments) {
    logger.error(format, arguments);
  }

  @Override
  public void debug(final String format, final Object... arguments) {
    logger.debug(format, arguments);
  }

}
