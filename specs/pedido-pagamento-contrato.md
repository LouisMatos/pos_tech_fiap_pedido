# Spec: Contrato de evento `EventoPedido` (pedido → pagamento)

> Formaliza um contrato que hoje é **implícito**: cada serviço mantém sua própria cópia da classe,
> sem schema compartilhado. Esta spec é a fonte de verdade — qualquer mudança de campo passa por aqui
> antes de tocar código dos dois lados. O producer (`pos_tech_fiap_pedido`) é dono do contrato.

## Contexto

Quando um pedido é criado, `pos_tech_fiap_pedido` publica um evento na fila `pedidos-mq`
(nome real, configurado em `mq.queues.pedidos` — ver `application-dev.yml`). `pos_tech_fiap_pagamento`
consome esse evento pra iniciar o processamento do pagamento. É o único ponto de acoplamento
entre os dois serviços — não há chamada HTTP direta, só mensagem.

## Requisitos

- Produtor: `PedidoPublisher.send(EventoPedido)` — `pos_tech_fiap_pedido/src/main/java/br/com/postechfiap/jlapppedido/infra/config/mq/PedidoPublisher.java`.
- Fila: `pedidos-mq` (`${mq.queues.pedidos}`).
- Serialização: JSON via Jackson `ObjectMapper` + `JavaTimeModule` (necessário pro `LocalDateTime`), payload publicado como string.
- Consumidor: `PedidosSubscriber.receive(String message)` — `pos_tech_fiap_pagamento/src/main/java/br/com/postechfiap/jlapppagamento/infra/mq/PedidosSubscriber.java` — desserializa pra `EventoPedidoDTO`.

## Não-Requisitos

- Não cobre o evento de retorno (pagamento → pedido, via fila `status-pedidos-mq`) — spec separada.
- Não cobre o evento pra cozinha (`EventoPedidoCozinha`, fila `cozinha-mq`) — outro contrato.
- Não propõe schema registry nem versionamento formal de mensagem — só documenta o formato atual e o processo de mudança.

## Critérios de Aceite

Dado um pedido criado com sucesso em `pos_tech_fiap_pedido`
Quando `PedidoPublisher.send(EventoPedido)` é chamado
Então uma mensagem JSON é publicada em `pedidos-mq` com exatamente os campos da seção Contrato

Dado uma mensagem válida em `pedidos-mq`
Quando `PedidosSubscriber.receive` consome
Então `PagamentoUseCase.processarPedido` desserializa sem erro pra `EventoPedidoDTO`

Dado que alguém precisa adicionar ou renomear um campo em `EventoPedido`
Quando a mudança é proposta
Então esta spec é atualizada primeiro, e a mudança é classificada como *breaking* ou *compatível* antes de qualquer PR

## Contrato

**Fila:** `pedidos-mq` · **Payload:** JSON string (não é `Message` estruturada — é `String` puro)

| Campo | Tipo | Produzido em | Observação |
|---|---|---|---|
| `id` | `Long` | `EventoPedido.id` | id interno do pedido |
| `numeroPedido` | `String` | `EventoPedido.numeroPedido` | identificador legível |
| `statusPagamento` | `StatusPagamento` (enum: `AGUARDANDO`, `APROVADO`, `NEGADO`) | `EventoPedido.statusPagamento` | serializado como string do nome do enum |
| `estado` | `Estado` (enum: `RECEBIDO`, `EM_PREPARACAO`, `PRONTO`, `FINALIZADO`) | `EventoPedido.estado` | estado de produção do pedido, não de pagamento |
| `dataPedido` | `LocalDateTime` | `EventoPedido.dataPedido` | exige `JavaTimeModule` registrado nos dois lados |
| `valorPedido` | `BigDecimal` | `EventoPedido.valorPedido` | total do pedido |

**Do lado do consumidor** (`EventoPedidoDTO`, em `pos_tech_fiap_pagamento`): mesmos 6 campos acima **mais**
`idMongoDB` (`String`), preenchido só depois da persistência em Mongo — não vem do producer, é local ao pagamento.

## Casos de Borda

- Campo novo em `EventoPedido` sem estar nesta spec -> revisor rejeita o PR até a spec ser atualizada.
- Renomear um campo existente -> é *breaking*: exige spec atualizada + deploy coordenado dos dois serviços (não é retrocompatível, pagamento vai falhar a desserialização).
- `pagamentoUseCase.processarPedido` retorna `false` (falha silenciosa hoje, só loga erro) -> fora do escopo desta spec, mas é um Não-Requisito que virou candidato a spec própria (ver Aprendizados no `spec-template.md`).

## Constitution check

- [x] Contrato de mensagem declarado explicitamente nesta spec, não só no código (ver `constitution.md`)
- [x] Producer (`pos_tech_fiap_pedido`) é dono do contrato — arquivo mora no repositório dele
- [ ] Ainda não migrado pra um formato com schema compartilhado entre os dois repos — próximo passo natural, fora do escopo desta versão
