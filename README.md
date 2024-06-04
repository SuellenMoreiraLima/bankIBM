# Banco IBM: 

## Sumário
1. [Introdução](#introdução)
2. [Pré-requisitos](#pré-requisitos)
3. [Subindo o Backend](#subindo-o-backend)
4. [Subindo o Frontend](#subindo-o-frontend)

## Introdução
Este documento fornece um passo a passo detalhado para configurar e executar o backend e frontend do projeto.

## Pré-requisitos
Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:
- Java JDK
- Maven
- Node.js e npm
- Angular CLI

## Subindo o Backend

### Passo 1: Clone o Repositório
- Clone o repositório do projeto:
  
  git clone https://github.com/SuellenMoreiraLima/bankIBM.git
- Entre no diretório da aplicação:
 cd bankIBM

### Passo 2: Build e Execute a Aplicação
No diretório do projeto, execute:

./mvnw clean install  
./mvnw spring-boot:run

### Passo 3: Acesse a Aplicação
A aplicação Spring Boot estará disponível em http://localhost:8080 

Observação: Banco de Dados
A aplicação usa o banco de dados H2, que é um banco de dados em memória configurado no application.properties. Para visualizar o banco de dados, acesse:

http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb  
Username: teste  
Password: teste  

## Subindo o Frontend

### Passo 1: Clone o Repositório
Clone o repositório do projeto:

git clone https://github.com/SuellenMoreiraLima/ibmBankFrontend.git  
cd ibmBankFrontend  

### Passo 2: Instale as Dependências
No diretório do projeto, execute:

npm install

### Passo 3: Execute a Aplicação
No diretório do projeto, execute:

ng serve

#### Passo 4: Acesse a Aplicação

O frontend Angular estará disponível em http://localhost:4200.

### Para dúvidas ou problemas, entre em contato com Suellen Lima.
### developer.suellen.lima@gmail.com
