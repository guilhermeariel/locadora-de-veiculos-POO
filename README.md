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
- Projeto preparado para gravação futura em arquivo (opcional)

---

## 📐 Regras de Negócio

- 🚫 Não permite duplicidade de **placas** ou **CPF/CNPJ**
- 🚘 Tipos de veículos:
  - PEQUENO → R$ 100/dia
  - MÉDIO → R$ 150/dia
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

## 📦 Estrutura de Pacotes

