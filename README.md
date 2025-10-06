# locadora-de-veiculos-POO
# 🚗 ADA LocateCar — Sistema de Locadora de Veículos (Java)

Projeto desenvolvido para aplicar os conceitos fundamentais de **Programação Orientada a Objetos (POO)** com foco em boas práticas como **SOLID**, **Generics** e **design modular**. O sistema simula uma locadora de veículos com cadastro de clientes (PF/PJ), controle de aluguéis e cálculo de valor total com regras de desconto.

---

## 🧩 Funcionalidades

- Cadastro e consulta de **veículos**
- Cadastro de **clientes pessoa física** e **jurídica**
- Registro de **aluguel e devolução** com controle de datas
- Cálculo de valor com base em **tipo de veículo** e **dias alugados**
- Aplicação automática de **descontos por perfil de cliente**
- Paginação nas listagens (bônus)
- Gravação em arquivo (bônus)
- Interface gráfica com JavaFX

---

## 📐 Regras de Negócio

- 🚫 Não permite duplicidade de **placas** ou **CPF/CNPJ**
- 🚘 Tipos de veículos:
  - HATCH → R$ 100/dia
  - SEDAN → R$ 150/dia
  - SUV → R$ 200/dia
- 🕓 Aluguel com hora fracionada é contado como diária cheia
- 💸 Descontos:
  - PF → 5% para mais de 5 diárias
  - PJ → 10% para mais de 3 diárias
- 🚫 Veículo alugado não pode ser alugado novamente até ser devolvido

---

## 🛠️ Tecnologias e Conceitos Aplicados

- ✅ **Java 17+**
- ✅ **POO (encapsulamento, herança, polimorfismo)**
- ✅ **Princípios SOLID**
  - SRP, OCP, LSP, ISP, DIP
- ✅ **Generics** com repositórios reutilizáveis
- ✅ **Enum** para tipos de veículos e valores fixos
- ✅ **Strategy Pattern** para cálculo de desconto
- ✅ **Separação por pacotes**: `model`, `repository`, `app`

---

## Refatoraçao Funcional (versao 1.1)

Projeto refatorado para aplicar conceitos de **Java Funcional**, incluindo Streams, Comparator, lambdas, Predicate, Function, Consumer, Supplier, Files, InputStream/OutputStream e interfaces funcionais.

---

## Refatorações realizadas

1. **Streams**
   - Filtragem de veículos alugados e aluguéis ativos usando `.stream().filter()`.
   - Paginação preparada com `skip()` e `limit()` (planejado para próximas implementações).

2. **Interfaces Funcionais**
   - **Function**: cálculo de valor de aluguel e criação de novos objetos (Veículo).
   - **Predicate**: validação de CPF, CNPJ e placas.
   - **Consumer**: logs formatados de cadastro, busca, devolução e remoção.

3. **Testes Unitários**
   - Consumer: utilizado para validar execuções de ações como salvar, atualizar e remover, simulando logs e callbacks centralizados.

4. **Paginaçao e Ordenaçao**
   - Paginação genérica e funcional baseada em: número da página; quantidade de itens por página; ordenação crescente ou decrescente.
   - Implementação genérica através da classe utilitária PaginacaoUtil.
   - Métodos de fácil uso nos repositórios:
     - buscarClientesPaginadosEOrdenados(int pagina, int tamanhoPagina, boolean ascendente)
     - buscarVeiculosPaginadosEOrdenados(int pagina, int tamanhoPagina, boolean ascendente)
     - buscarAlugueisPaginadosEOrdenados(int pagina, int tamanhoPagina, boolean ascendente)
   - Uso de paginação em interface gráfica usando FX

5. **Gerador de dados de teste**
   - Supplier: Utilizado para gerar uma série de dados para poder visualizar o funcionamento da interface;
---

## Principais melhorias percebidas

- Código mais **limpo e conciso** com operações funcionais.
- Maior **reutilização de lógica**, evitando duplicação de validações e logs.
- Testes unitários mais **padronizados**, utilizando **Supplier** e mocks consistentes.
- Preparação para **futuras implementações de Streams e paginação**.
- Logs e mensagens de operação centralizados via `Consumer`.
- Maior padronização e legibilidade dos testes.
- Redução de código duplicado nos testes.
- Facilidade em validar efeitos colaterais (ex.: logs e callbacks) de forma isolada.

---
  
## Dificuldades enfrentadas

- Adaptar métodos existentes para o padrão funcional sem quebrar a lógica de negócio.
- Implementar o uso de Predicate, Function, Consumer e Supplier, mantendo os mesmos nomes e funcionamentos das funções 
existentes.
- Pensar em uma paginação genérica e reutilizável, que pudesse ser aplicada também para a interface gráfica.

