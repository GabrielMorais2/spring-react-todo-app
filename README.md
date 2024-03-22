# Spring Boot and React To-Do App

### Summary
This is yet another to-do app. Like the other _-probably-_ millions of similar to-do apps on the Internet, this app can be used for keeping track of your tasks.
Allows you to create tasks on its simple and clean interface. You can also mark the tasks as completed or delete them.
This was a Spring Boot starter project for me.

### Özet
Bu, bir başka yapılacaklar listesi uygulaması. İnternet'teki diğer _-muhtemelen-_ milyonlarca benzer yapılacaklar listesi uygulaması gibi bu uygulama da görevlerinizi takip etmek için kullanılabilir.
Basit ve temiz arayüzü ile görevler oluşturmanıza izin verir. Görevleri tamamlandı olarak işaretleyebilir veya silebilirsiniz.
Benim için Spring Boot'a başlangıç projesiydi.


### Technologies
- JDK 17
- Spring Boot with Maven
- Swagger
- PostgreSQL
- React
- Chakra UI
- Vite
- TypeScript
- Docker

### Preview
https://github.com/ahmethakanbesel/spring-react-todo-app/assets/6422460/27e2f6c2-31e7-4e18-b955-fc9f33b062f8

### Running

##### Backend

Firstly, you need to initialize a PostgreSQL database. You can do that with Docker:
```shell
docker run --name postgres -e POSTGRES_PASSWORD=secret POSTGRES_USER=myuser -p 5432:5432 -d postgres
```

After initializing the database, you need to set the database credentials in the `/src/main/resources/application.properties` file.

Then, you can run the backend with the following command:

```shell
mvn spring-boot:run
```

##### Frontend

Initially, create a copy of `.env.example` file and rename it to `.env`.
Then, you need to fill the required fields for configuring the backend connection.
You can leave the `VITE_API_URL` field as it is, if you are running the backend on the same machine.
After that, you can run the frontend with the following commands:

```shell
bun install
bun run dev
```

> `bun` is used during the development of this app but the commands above can be replaced with `npm`, `yarn` or `pnpm`.

### Deployment

The app can be deployed with Docker by default.
At the beginning, build the backend `.jar` file with the following command:

```shell
mvn clean install
```

This command will build the app into a `.jar` file in the `/target` directory.

After building the backend app you need to set some environment variables for the all services.
You can find an example of the environment variables in the `.env.example` file.

After setting the environment variables, you can run `docker compose` to run the other services.

```shell
docker compose up -d
```

It will build the frontend app and run the backend and frontend services with a PostgreSQL database.
