# Spec: Upgrade do Spring Boot 3.2.5 nos 3 microsserviços

> Cobre `pos_tech_fiap_pedido` (piloto), `pos_tech_fiap_pagamento` e `pos_tech_fiap_producao`.
> Fica no pedido porque ele é o piloto e já tem `specs/`; os outros dois seguem esta mesma spec.

## Contexto

O CI dos 3 serviços está vermelho no `dependency-check-maven:10.0.4:check` (`failBuildOnCVSS=7`) desde o G1 da
feature `.prd-implementacoes/2026-11-22-infra-dev-enxuta`. Lá, a falha foi registrada como débito e o
`verify` local rodou com `-Ddependency-check.skip=true`. Todas as libs apontadas (lista em Contrato) vêm do
`spring-boot-starter-parent` 3.2.5: nenhum pom declara versão própria. Esta spec tira o débito com o menor
passo que zera o gate, sem desligar nem afrouxar a checagem.

## Requisitos

- **Etapa 1 (esta spec):** subir o `spring-boot-starter-parent` de 3.2.5 para a **última 3.5.x** nos 3 poms, na mesma
  versão nos 3. A versão exata vem do Maven Central no momento da implementação, sem chute. Antes de codar:
  1. confirmar na página de suporte do Spring Boot se a 3.5.x ainda recebe correção OSS;
  2. ler as release notes de 3.3, 3.4 e 3.5 e listar no PR o que afeta estes serviços.
- A única mudança de código esperada é a exigida pelas release notes. Se nenhum item delas tocar estes serviços, o diff é só a linha do parent.
- Nenhuma dependência ganha versão explícita para "escapar" do BOM. Uma exceção só entra se uma CVE CVSS ≥ 7 tiver
  correção numa versão compatível da lib e ainda não tiver chegado ao BOM da 3.5.x. Ela precisa de uma propriedade
  de override do Boot (ex.: `<tomcat.version>`), com comentário citando a CVE e uma data de revisão.
- `./mvnw -q clean verify` passa **com** o dependency-check ligado, sem `-Ddependency-check.skip`, nos 3 serviços.
- **Etapa 2 (spec própria, se necessária):** se depois da Etapa 1 sobrar CVE CVSS ≥ 7 sem correção na linha 3.x,
  abre-se `specs/upgrade-spring-boot-4.md` para migrar para 4.x. As PRs do Dependabot (pedido #22, pagamento #24,
  3.2.5 → 4.1.0) servem de referência, não de merge. Essa etapa envolve Spring Framework 7 e Jackson 3, que troca
  o pacote `com.fasterxml.jackson` e hoje está em 23 arquivos (pedido 12, pagamento 7, producao 4).
- Ordem: o pedido primeiro, validado de ponta a ponta. Depois o pagamento e o producao, cada um numa PR própria em `feature/upgrade-spring-boot`.

## Não-Requisitos

- Não migra para Spring Boot 4.x (Etapa 2, outra spec).
- Não sobe o `dependency-check-maven` (10.0.4), o JaCoCo (0.8.12) nem o Checkstyle (3.5.0). Exceção: se o
  `dependency-check` 10.0.4 não conseguir ler o feed atual do NVD, o problema é registrado e o upgrade do plugin vira item separado.
- Não reduz os mínimos do JaCoCo, não desativa o Checkstyle e não sobe o `failBuildOnCVSS`.
- Não cria arquivo de supressão do dependency-check nesta etapa. CVE sem correção na 3.x empurra para a Etapa 2, não para supressão.
- Não troca a versão do Java (continua 21) nem a imagem base (`eclipse-temurin:21`).
- Não mexe no monólito `pos_tech_fiap` nem na Lambda.
- Não mexe nos PRs abertos do Dependabot (não fecha nem faz merge).

## Critérios de Aceite

Dado o pom de cada serviço com `spring-boot-starter-parent` na última 3.5.x
Quando roda `./mvnw -q clean verify` sem `-Ddependency-check.skip`
Então o build passa (rc=0) e o relatório do dependency-check não tem nenhuma dependência com CVSS ≥ 7

Dado o mesmo build
Quando JaCoCo e Checkstyle rodam
Então passam com os mesmos mínimos e regras de hoje (diff do pom só na versão do parent e, se houver, em overrides documentados)

Dado os testes atuais (pedido 71, pagamento 19, producao 16; medido em 2026-11-24)
Quando roda o `verify`
Então todos passam, sem `@Disabled` novo nem teste removido

Dado o pedido publicando `EventoPedido` em `pedidos-mq` com o Boot novo
Quando o pagamento (ainda no 3.2.5 ou já no novo) consome
Então desserializa sem erro, e o JSON tem os mesmos 6 campos, nomes e formato de `dataPedido` de
`specs/pedido-pagamento-contrato.md` (ver Contrato)

Dado os 3 serviços no Boot novo, rodando juntos (docker-compose local ou dev na AWS)
Quando roda o fluxo E2E do G3 (cliente → pedido → webhook APROVADO → `GET /api/v1/producao/{numero_pedido}`)
Então o pedido aparece no producao com `statusPagamento: APROVADO`, como no G3 de 2026-11-27

Dado que sobrou alguma CVE CVSS ≥ 7 sem correção na linha 3.x
Quando a Etapa 1 termina
Então o PR registra a lista (lib, versão, CVE, CVSS) e a decisão de abrir a Etapa 2, sem supressão e sem baixar o gate

## Contrato

Nenhum contrato de mensagem muda. A mudança é **compatível** por exigência: o upgrade não pode alterar o formato dos eventos.

Eventos que dependem da serialização Jackson e precisam continuar idênticos:

| Evento / fluxo | Producer | Consumer | Spec |
| --- | --- | --- | --- |
| `EventoPedido` em `pedidos-mq` | pedido (`infra/config/mq/PedidoPublisher`) | pagamento (`infra/mq/PedidosSubscriber`) | `specs/pedido-pagamento-contrato.md` |
| `EventoPedidoCozinha` em `cozinha-mq` | pedido (`infra/config/mq/PedidoPublisher`) | producao (`infra/mq/PedidosSubscriber`) | implícito (sem spec) |
| status em `status-pedidos-mq` | pagamento e producao (`infra/mq/PedidoPublisher` de cada) | pedido (`StatusPagamentoPedidosSubscriber`) | implícito (sem spec) |

Pontos de atenção na serialização: `LocalDateTime` com `JavaTimeModule`, enums por nome, `BigDecimal`. Se o Jackson
da 3.5.x mudar qualquer default que altere o JSON, o mapper é configurado para manter o formato atual; o contrato não muda.

CVEs que motivam a spec (OWASP `dependency-check` 10.0.4, rodado em 2026-11-24, CVSS ≥ 7):

| Lib (versão atual, vinda do BOM 3.2.5) | Serviços | CVEs (exemplos) |
| --- | --- | --- |
| `spring-core`/`spring-web` 6.1.6 | 3 | CVE-2026-47884 (9.8), CVE-2026-59313 (9.8) e mais 14 |
| `tomcat-embed-core` 10.1.20 | 3 | CVE-2025-24813 (9.8), CVE-2024-50379 (9.8) e mais 42 |
| `jackson-databind` 2.15.4 | 3 | CVE-2026-54512 (8.1), CVE-2026-54513 (8.1) |
| `log4j-api` 2.21.1 | 3 | CVE-2026-34478..34481 (7.5) |
| `amqp-client` 5.19.0 | 3 | CVE-2026-106121, CVE-2026-106122 (7.5) |
| `angus-activation` 2.0.2 | pedido, producao | CVE-2025-7962 (7.5) |
| `postgresql` 42.6.2 | pedido | CVE-2026-42198 (7.5) |
| `mongodb-driver-core` 4.11.2, `spring-data-mongodb` 4.2.5 | pagamento | CVE-2026-88033 (8.3), CVE-2026-41717 (8.1) |

A lista completa sai do `target/dependency-check-report.html` de cada serviço. O PR anexa o resumo antes/depois.

## Casos de Borda

- A 3.5.x já saiu do suporte OSS e não corrige as CVEs de 2026: a Etapa 1 ainda vale, porque reduz a lista, mas o gate não zera. Registra no PR e abre a Etapa 2.
- A release note exige mudança de config, como propriedade renomeada ou default de Hibernate/Actuator alterado: aplica só o que a nota pede e cita a nota no commit.
- O teste quebra por comportamento novo do Spring: corrige o código de produção se o comportamento novo for o certo; se não, ajusta o teste explicando no commit. Nunca `@Disabled`.
- Mudar o `ddl-auto` do producao dev (`update`) ou o dialect do pedido (`PostgreSQLDialect` explícito): fora de escopo; só mexer se o Hibernate novo rejeitar a config, e citar o motivo.
- Dois serviços em versões diferentes do Boot durante o rollout (pedido novo, pagamento antigo): os eventos precisam ser trocados nos dois sentidos sem erro. É o critério de compatibilidade do Contrato.
- O NVD fora do ar ou o dependency-check sem conseguir atualizar a base: o build falha por infraestrutura, não por CVE. Registrar e repetir; não usar `skip`.

## Constitution check

- [x] Respeita o layering do serviço (`domain/usecase/infra`, sem lógica de negócio em controller/adapter): não cria nem move lógica, é upgrade de plataforma.
- [x] Se cruza serviços, o contrato de mensagem está declarado nesta spec (não só no código): a seção Contrato lista os 3 fluxos e aponta `pedido-pagamento-contrato.md`.
- [x] Se muda um contrato existente, marcado explicitamente como *breaking* ou *compatível*: nenhum contrato muda, e compatível é exigência (critério de aceite do `EventoPedido`).
