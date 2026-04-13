<div align="center">

# 🎫 API HelpDesk

**Sistema REST para gerenciamento de chamados técnicos**

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)](https://github.com/)

<br/>

> API REST desenvolvida com **Java 21 + Spring Boot + PostgreSQL**, simulando um sistema real de suporte técnico, com regras de negócio bem definidas, arquitetura em camadas e boas práticas de desenvolvimento backend.
</div>

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Funcionalidades](#-funcionalidades)
- [Regras de Negócio](#-regras-de-negócio)
- [Objetivo](#-objetivo)

---

## 💡 Sobre o Projeto

A **API HelpDesk** é um sistema backend desenvolvido para simular um cenário real de suporte técnico corporativo. O projeto prioriza:

- ✅ **Organização e legibilidade** do código
- ✅ **Validações robustas** com Bean Validation
- ✅ **Regras de negócio** próximas às utilizadas no mercado
- ✅ **Arquitetura em camadas** com baixo acoplamento e alta coesão
- ✅ **Tratamento global de exceções** centralizado

---

## 🛠️ Tecnologias

| Tecnologia | Finalidade |
|---|---|
| **Java 21** | Linguagem principal (LTS) |
| **Spring Boot** | Framework web |
| **Spring Data JPA** | Persistência de dados |
| **Hibernate** | ORM / mapeamento objeto-relacional |
| **PostgreSQL** | Banco de dados relacional |
| **Bean Validation** | Validação de dados de entrada |
| **Maven** | Gerenciamento de dependências |
| **Postman** | Testes e documentação da API |

---

## 🧱 Arquitetura

O projeto segue o padrão de **arquitetura em camadas**, garantindo separação clara de responsabilidades:

```
Client (Postman / Frontend)
        │
        ▼
┌─────────────────┐
│   Controller    │  ← Recebe requisições HTTP, delega para o Service
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│    Service      │  ← Contém todas as regras de negócio
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   Repository    │  ← Acesso ao banco de dados via Spring Data JPA
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   PostgreSQL    │  ← Persistência dos dados
└─────────────────┘
```

**Padrões e boas práticas utilizados:**

- 📦 **DTOs** para desacoplamento entre camadas e segurança dos dados
- 🔍 **Bean Validation** com `@Valid` para validação declarativa
- ⚠️ **Global Exception Handler** para tratamento centralizado de erros
- 🏗️ **Entidades JPA** com mapeamento objeto-relacional

---

## ⚙️ Funcionalidades

### 👤 Usuários

| Funcionalidade | Descrição |
|---|---|
| Cadastro | Registro de novos usuários no sistema |
| Validação única | Email e telefone únicos garantidos |
| Busca por nome | Filtragem de usuários pelo nome |
| Busca por email | Filtragem de usuários pelo email |
| Busca por telefone | Filtragem de usuários pelo telefone |
| Controle de papéis | `USER` · `TECHNICAL` · `ADMIN` |

### 🎫 Chamados (Tickets)

| Funcionalidade | Descrição |
|---|---|
| Criação | Abertura de novos chamados |
| Atribuição | Vinculação de técnico responsável |
| Filtro por título | Busca por palavras-chave no título |
| Filtro por data | Filtragem por data de abertura |
| Filtro por status | `OPEN` · `CLOSED` |
| Filtro por prioridade | `LOW` · `MEDIUM` · `HIGH` |
| Filtro por solicitante | Chamados por usuário solicitante |
| Filtro por técnico | Chamados por técnico responsável |
| Atualização | Edição de chamados em aberto |
| Exclusão | Remoção de chamados |

---

## 🧠 Regras de Negócio

```
🔒  Apenas usuários com role TECHNICAL podem ser atribuídos a chamados
🚫  Chamados com status CLOSED não podem ser alterados
🔁  Chamados fechados não podem ser reabertos
⚠️  Um chamado só pode ser fechado se houver técnico atribuído
📌  Status padrão na criação: OPEN
📌  Prioridade padrão na criação: LOW
```

Todas as regras são validadas e aplicadas na **camada de serviço**, garantindo que a lógica de negócio esteja centralizada e desacoplada dos controllers.

---

## 🎯 Objetivo

- Desenvolvimento de **APIs REST** com Spring Boot
- Aplicação de **boas práticas** de arquitetura backend
- Implementação de **regras de negócio reais** e validações robustas
- **Organização e manutenção** de código em projetos profissionais

---

<div align="center">

Desenvolvido por **Arthur Lucas** · Estudante de Engenharia de Software · SENAI FATESG

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/arthur-lucas0910)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/arthurllucass)

</div>
