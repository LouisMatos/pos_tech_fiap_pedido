package br.com.postechfiap.jlapppedido.infra.cliente.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteDTO;
import br.com.postechfiap.jlapppedido.domain.cliente.dto.ClienteExclusaoDTO;
import br.com.postechfiap.jlapppedido.shared.logger.log.Logger;
import br.com.postechfiap.jlapppedido.usecase.cliente.ClienteUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/clientes")
public class ClienteController {

  private final ClienteUseCase clienteUseCase;

  private final Logger log;

  public ClienteController(ClienteUseCase clienteUseCase, Logger log) {
    this.clienteUseCase = clienteUseCase;
    this.log = log;
  }

  @PostMapping
  public ResponseEntity<ClienteDTO> inserir(@Valid @RequestBody ClienteDTO clienteDTO) {
    log.info("Iniciando o cadastro de cliente");
    return ResponseEntity.ok().body(clienteUseCase.inserir(clienteDTO));
  }

  @GetMapping("/{cpf}")
  public ResponseEntity<ClienteDTO> buscarClientePorCpf(@PathVariable final String cpf) {
    log.info("Iniciando a busca do cliente com o cpf: {}", cpf);
    return ResponseEntity.ok().body(clienteUseCase.buscarClientePorCpf(cpf));
  }

  @DeleteMapping("/{cpf}")
  public ResponseEntity<Void> deletarClientePorCpf(@PathVariable final String cpf,
      @RequestBody ClienteExclusaoDTO clienteExclus) {
    log.info("Iniciando a exclusão dos dados de cliente com o cpf: {}", cpf);
    clienteUseCase.deletarClientePorCpf(cpf, clienteExclus);
    return ResponseEntity.noContent().build();
  }

}
