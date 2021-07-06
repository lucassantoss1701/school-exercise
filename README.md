# Projeto school

## Sobre esse projeto

Esta é uma API simples que tem o objetivo de gerenciar usuários, cursos e matrículas.

São utilizadas as tecnologias:

- Java 11
- Maven 3+  
- Spring Boot
- Spring Web
- Bean Validation
- Spring Data JPA
- H2, o BD relacional em memória

Abra o projeto na IDE da sua preferência.

Execute os testes automatizados. 

Suba a aplicação e explore a API com uma ferramenta como [cURL](https://curl.se/), [Insomnia](https://insomnia.rest/), [Postman](https://www.postman.com/).

Alguns exemplos de chamadas usando cURL estão em [exemplos-curl.md](exemplos-curl.md).

### O que já está implementado?

Os seguintes endpoints estão implementados:

- `GET /users/{username}` obtém os detalhes de um usuário
- `POST /users` adiciona um novo usuário
- `GET /courses` lista os cursos já cadastrado
- `POST /courses` adiciona um novo curso
- `GET /courses/{code}` obtém os detalhes de um curso

## Sobre o que você deve fazer

Devem ser implementadas as seguintes tarefas.

### 1: Correção dos testes automatizados

Nem todos os testes automatizados que já existem no projeto estão passando.

Altere o código de produção para que todos os testes automatizados passem.

Observação: **NÃO** modifique os testes atuais do projeto!

### 2: Implementar a Matrícula de usuário

Uma Matrícula associa um usuário a um curso, em uma data específica.

Um usuário não pode matricular-se mais de uma vez em um curso.

Implemente um endpoint no projeto que recebe um `POST` em `/courses/{courseCode}/enroll`.

O `username` do usuário a ser matriculado deve estar em um JSON no corpo da requisição.

Por exemplo, para matricular o usuário `alex` no curso de código `java-1`, deve ser feito o seguinte:

```
POST /courses/java-1/enroll
Content-Type: application/json

{"username": "alex"}
```

O _status code_ de retorno deve ser [201 Created](https://httpstatusdogs.com/201-created).
