# Serviço de Pedido (pos_tech_fiap_pedido)

Regras específicas deste serviço. As regras gerais do workspace estão em `../AGENTS.md`
(arquitetura em `../docs/ai/arquitetura.md`, contratos em `../docs/ai/contratos.md`).

## Comandos
- Um teste: `./mvnw -q test -Dtest=NomeDaClasseTest`
- Antes de concluir qualquer tarefa: `./mvnw -q verify` (inclui Checkstyle e JaCoCo, igual ao CI)
- Subir dependências locais: `docker compose up -d`

## Mensageria
- Publica em: `pedidos-mq` (EventoPedido), `cozinha-mq` (EventoPedidoCozinha) — `infra/config/mq/PedidoPublisher`
- Consome de: `status-pedidos-mq` — `infra/config/mq/StatusPagamentoPedidosSubscriber`
- Nomes das filas em `src/main/resources/application-*.yml` (`mq.queues.*`). Não fixe nomes no código.

## Regras
- Cobertura mínima do JaCoCo: 75%. Código novo vem com teste no pacote espelhado em `src/test`.
- Regra de negócio em `domain`/`usecase`; `infra` só adapta (controller, gateway, db, mq).
- Este serviço é dono do contrato `EventoPedido`: qualquer mudança passa antes por `specs/pedido-pagamento-contrato.md`.
- Specs deste serviço ficam em `specs/`.
