package br.com.postechfiap.jlapppedido.infra.pedido.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoAcompanhamentoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.PedidoDTO;
import br.com.postechfiap.jlapppedido.domain.pedido.dto.StatusPedidoDTO;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.usecase.pedido.PedidoUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/pedidos")
public class PedidoController {

  private final PedidoUseCase pedidoUseCase;

  private final Logger log;

  public PedidoController(PedidoUseCase pedidoUseCase, Logger log) {
    this.pedidoUseCase = pedidoUseCase;
    this.log = log;
  }

  @PostMapping
  public ResponseEntity<PedidoDTO> inserir(@Valid @RequestBody PedidoDTO pedidoDTO) {
    log.info("Iniciando o cadastro de Pedido!");
    return ResponseEntity.ok().body(pedidoUseCase.inserir(pedidoDTO));
  }

  @GetMapping
  public ResponseEntity<List<PedidoDTO>> buscarTodos() {
    log.info("Iniciando a busca de todos os Pedidos!");
    return ResponseEntity.ok().body(pedidoUseCase.buscarTodos());
  }

  @GetMapping("/{numero_pedido}/status_pagamento")
  public ResponseEntity<StatusPedidoDTO> buscarStatusPagamentoPedido(
      @PathVariable final String numero_pedido) {
    log.info("Iniciando a busca pelo status do pedido: {}", numero_pedido);
    return ResponseEntity.ok().body(pedidoUseCase.buscarStatusPagamentoPedido(numero_pedido));
  }

  @GetMapping("/acompanhamento")
  public ResponseEntity<List<PedidoAcompanhamentoDTO>> buscarPedidosAcompanhamento() {
    log.info("Iniciando a busca dos pedidos para o acompanhamento!");
    return ResponseEntity.ok().body(pedidoUseCase.buscarPedidosAcompanhamento());
  }



}
