package br.com.postechfiap.jlapppedido.shared.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import jakarta.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private Logger log;

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<StandardError> badRequestException(BadRequestException e,
      HttpServletRequest request) {
    log.warn(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new StandardError(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<StandardError> notFoundException(NotFoundException e,
      HttpServletRequest request) {
    log.warn(e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new StandardError(HttpStatus.NOT_FOUND, e.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(UnprocessableEntityException.class)
  public ResponseEntity<StandardError> unprocessableEntityException(UnprocessableEntityException e,
      HttpServletRequest request) {
    log.warn(e.getMessage());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new StandardError(
        HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<StandardError> runtimeException(RuntimeException e,
      HttpServletRequest request) {
    log.warn(e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StandardError(
        HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handlerException(Exception e, Object body, HttpStatus httpStatus,
      HttpHeaders headers, WebRequest webRequest) {
    log.warn(e.getMessage());
    return ResponseEntity.status(httpStatus)
        .body(new StandardError(httpStatus, e.getMessage(), webRequest.getContextPath()));
  }

}
