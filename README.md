# Sistema de Gerenciamento de Tarefas - API REST

Aplicação back-end para gerenciamento de tarefas através de requisições HTTP(s) seguindo a convenção REST.

## Como funciona?
Este é um projeto é uma API REST para atender uma aplicação de tarefas. Ele possui a entidade:
- Task

<details>
  <summary><h3>Endpoints</h3></summary>

  ### POST /task/
  #### Entrada
  ```json
  {
    "description": "levar o cachorro para passear",
    "status": "pending or finished",
    "image": file (opcional)
  }
  ```
  #### Saída
  ```
  status 201
  ```

  ### POST /task/finish/{id}
 
  #### Saída
  ```
  status 204
  ```
  ### GET /task/

  #### Saída
  ```json
  [
    {
      "id": "afa02595-d216-45d7-af5c-ac09cf977d84",
      "description": "levar o gato para passear",
      "status": "finished",
      "image": file
    },
    {
      "id": "56f4bb7b-958d-47f6-b938-c39211cf981a",
      "description": "ir no mercado",
      "status": "pending",
      "image": null
    },
    {
      "id": "d119cdfb-26bb-4865-9493-8621f4a97be5",
      "description": "fazer yoga",
      "status": "pending",
      "image": file
    }
  ]
  ```

  ### PUT /task/{id}

  #### Entrada
  ```json
  {
    "description": "levar o cachorro para passear",
    "status": "pending",
    "image": file (opcional)
  }
  ```
  #### Saída
  ```json
  {
    "id": "afa02595-d216-45d7-af5c-ac09cf977d84",
    "description": "levar o cachorro para passear",
    "status": "pending",
    "image": file
  }
  ```
</details>

## Como rodar o projeto

1. Clone este repositório.
2. Instale as dependências usando Maven.
3. Configure seu banco de dados MySQL em um arquivo chamado `application.properties` na raiz do projeto, conforme sua necessidade.
4. Ao rodar o projeto as tabelas necessárias serão criadas no seu banco.
6. Execute a aplicação`.
7. Acesse a API em [http://localhost:8080/task/](http://localhost:8080/task/).

## Tecnologias Utilizadas

- Java
- Spring Boot
- Hibernate
- Jackson
- MySQL
