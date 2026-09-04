# lab-design-patterns-spring

API REST em Spring Boot construída como projeto de desafio, com o objetivo de demonstrar, na prática, os principais **Design Patterns do GoF (Gang of Four)** aplicados a um domínio real de e-commerce (pedidos, pagamentos e frete).

Todo o projeto foi desenvolvido manualmente, passo a passo, com foco em entender o **problema que cada padrão resolve**, não apenas em replicar sua estrutura.

## Sobre o projeto

O domínio simula um fluxo de checkout de e-commerce: um cliente monta um pedido com produtos, o pedido passa por validações de negócio, tem o frete calculado, o pagamento é processado por um método à escolha, e o status do pedido evolui através de um ciclo de vida controlado (`CREATED → PAID → SHIPPED → DELIVERED`, com possibilidade de `CANCELLED`).

Cada uma dessas etapas foi construída usando um ou mais padrões de projeto clássicos, listados abaixo.

## Tecnologias

- **Java 21**
- **Spring Boot 4.1.1** (Web, Data JPA, Validation)
- **H2 Database** (banco em memória)
- **Lombok**
- **springdoc-openapi** (documentação interativa da API)
- **JUnit 5 + AssertJ + Mockito** (testes)
- **Maven**

## Design Patterns implementados

| Padrão | Categoria | Onde está | O que resolve |
|---|---|---|---|
| **Builder** | Criacional | `service/builder/OrderBuilder.java` | Constrói um `Order` complexo passo a passo (itens, validação de estoque, cálculo de total), garantindo que o objeto só exista em estado válido. |
| **Strategy** | Comportamental | `service/shipping/` + `ShippingCalculator.java` | Permite trocar o algoritmo de cálculo de frete (Standard, Express, Free) em tempo de execução, sem `if/else` acoplado. |
| **Factory Method** | Criacional | `service/payment/PaymentProcessorFactory.java` | Decide qual `PaymentProcessor` instanciar (Pix, Cartão, Boleto, Gateway externo) a partir de um `PaymentType`, com checagem exaustiva em tempo de compilação. |
| **Observer** | Comportamental | `service/event/` | Notifica múltiplos interessados (notificação, auditoria) quando o status de um pedido muda, sem que quem publica o evento conheça quem reage a ele. Implementado com `ApplicationEventPublisher` nativo do Spring. |
| **State** | Comportamental | `service/state/` | Modela o ciclo de vida do pedido como objetos de estado, cada um sabendo quais transições são válidas — impede pular etapas (ex: ir de `CREATED` direto para `DELIVERED`). |
| **Chain of Responsibility** | Comportamental | `service/validation/` | Encadeia validações independentes (estoque, limite de valor, fraude) antes de confirmar um pedido, cada handler responsável só pela própria regra. |
| **Decorator** | Estrutural | `service/shipping/ShippingDecorator.java` + `InsuranceShippingDecorator.java` | Adiciona comportamento extra (seguro de frete) por cima de uma strategy de frete existente, sem alterá-la. |
| **Facade** | Estrutural | `service/CheckoutFacade.java` | Ponto de entrada único que orquestra Builder, Chain, Strategy, Factory e State num fluxo de checkout completo, escondendo a complexidade de coordenar 6+ subsistemas. |
| **Adapter** | Estrutural | `service/payment/PaymentGatewayAdapter.java` | Traduz a resposta de um gateway de pagamento externo simulado (formato incompatível) para o `PaymentResult` usado internamente, encaixando-se na mesma interface `PaymentProcessor` dos demais métodos de pagamento. |

## Estrutura do projeto

```
src/main/java/com/joaodev/labdesignpatternsspring/
├── domain/          # Entidades JPA e enums de domínio
├── repository/      # Interfaces Spring Data JPA
├── service/         # Regras de negócio e os design patterns
│   ├── builder/
│   ├── event/
│   ├── payment/
│   │   └── gateway/
│   ├── shipping/
│   └── validation/
├── controller/       # Endpoints REST
├── dto/             # Objetos de request/response
└── exception/        # Exceptions customizadas + handler global
```

## Como rodar o projeto

Pré-requisitos: Java 21 e Maven.

```bash
mvn spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

## Como testar via Swagger UI

O projeto usa **springdoc-openapi**, que gera a documentação interativa automaticamente a partir dos controllers.

Com a aplicação rodando, acesse:

- **Interface interativa (Swagger UI):**
  `http://localhost:8080/swagger-ui.html`

- **Especificação OpenAPI crua (JSON):**
  `http://localhost:8080/v3/api-docs`

Pela Swagger UI é possível expandir cada endpoint, ver o formato exato de request/response esperado e disparar chamadas reais direto pelo navegador, sem precisar de Postman/Insomnia.

### Fluxo sugerido de teste

1. Popule o banco H2 (em memória) via endpoints de `Customer` e `Product`, se existirem, ou insira diretamente via console H2 (`/h2-console`, se habilitado).
2. `POST /api/orders/checkout` — monta e finaliza um pedido completo (Builder → Chain → Strategy → Factory → State), informando `customerId`, lista de itens (`productId` + `quantity`), `shippingType` e `paymentType`.
3. Se o pagamento não for aprovado na hora (ex: `BOLETO`), o pedido retorna com status `CREATED`. Use os endpoints de transição para avançar manualmente:
    - `POST /api/orders/{id}/pay`
    - `POST /api/orders/{id}/ship`
    - `POST /api/orders/{id}/deliver`
    - `POST /api/orders/{id}/cancel`
4. Tentar uma transição inválida (ex: `ship` num pedido ainda `CREATED`) retorna `422 Unprocessable Entity`, com mensagem explicando a regra violada — validação garantida pelo **State** pattern.

### Códigos de resposta

| Código | Situação |
|---|---|
| `200 / 201` | Sucesso |
| `400 Bad Request` | Erro de validação dos dados de entrada (`@Valid`) |
| `404 Not Found` | Cliente, produto ou pedido não encontrado |
| `422 Unprocessable Entity` | Regra de negócio violada (estoque insuficiente, limite excedido, transição de estado inválida, etc.) |
| `500 Internal Server Error` | Erro inesperado |

## Testes

O projeto conta com testes de integração (`@SpringBootTest`) e unitários (Mockito) cobrindo os principais fluxos, incluindo o caminho de checkout completo de ponta a ponta via `CheckoutFacade`.

```bash
mvn test
```

## Autor

João Augusto — [github.com/joaodev](https://github.com/joaodev)