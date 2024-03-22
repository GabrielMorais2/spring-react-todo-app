# Spring Boot and React To-Do App

Este é um projeto SpringTodoApp que consiste em uma aplicação web para gerenciamento de tarefas (to-do list), com um backend construído em Java Spring e um frontend desenvolvido em React. Na pasta docs, terá o arquivo para o teste de cada endpoint da aplicação via postmann.

### Tecnologias

- JDK 17
- Spring Boot with Maven
- Swagger
- PostgreSQL
- React
- Chakra UI
- Vite
- TypeScript
- Docker

## Video da aplicação funcionando

https://github.com/ahmethakanbesel/spring-react-todo-app/assets/6422460/27e2f6c2-31e7-4e18-b955-fc9f33b062f8

## Requerimentos

Para instalar a aplicação, algumas tecnologias são requeridas como:

Docker
Docker Compose

## Instalação

Para instalar a aplicação, siga esses passos:

- Clone o repositorio do GitHub:

```shel
  git clone https://github.com/GabrielMorais2/spring-react-todo-app-master
```

- Navegue até o diretorio da aplicação:

```shel
  cd spring-react-todo-app-master
```

- Execute o docker compose para instalar iniciar a aplicação:

```shel
  docker-compose -d up
```

A aplicação estará disponivel em http://localhost:3000/ pelo frontend com React.

## Para uso da API

A API oferece endpoints para realizar operações CRUD (Create, Read, Update, Delete) em tarefas. Abaixo estão os principais endpoints disponíveis:

- POST /api/tasks: Cria uma nova tarefa.
- GET /api/tasks: Retorna todas as tarefas.
- GET /api/tasks/{id}: Retorna uma tarefa específica com o ID fornecido.
- PUT /api/tasks/{id}: Atualiza uma tarefa existente com o ID fornecido.
- DELETE /api/tasks/{id}: Exclui uma tarefa com o ID fornecido.
- DELETE /api/tasks/: Exclui todas as tarefas com base no parâmetro de filtro fornecido.

Certifique-se de consultar a documentação da API fornecida pelo Swagger para obter detalhes sobre cada endpoint.

## Documentação da API

A documentação da API pode ser acessada via Swagger UI. Após iniciar o backend, abra seu navegador e acesse [http://localhost:8080/swagger-ui/](http://localhost:8080/todo/swagger-ui.html) para visualizar e interagir com a documentação.

![image](https://github.com/GabrielMorais2/spring-react-todo-app-master/assets/68476116/c2d25887-a21b-463b-81f7-ef2849093c8e)


## Funcionalidades para implementar:

- Spring Security
