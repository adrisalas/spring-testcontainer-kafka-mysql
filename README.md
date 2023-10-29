# spring-testcontainer-kafka-mysql

Example of a Spring Boot with Kotlin using testcontainer for a message queue (Kafka) and database (MySQL)

### How to run
- `mvn clean install -DskipTests`
- `mvn spring-boot:run`

### Structure
This is a proyect trying to follow a "Clean Architecture" but a little tweak, no interface would be defined to use any business stuff
Packages:
- `domain`: The main logic of the app, it does not require or import anything from another package
- `infrastructure`: Encompasses external components like databases, frameworks... enabling communication between the domain and external systems.

### Testcontainers
Important Annotations:

- `@DynamicPropertySource` -- To set properties of spring AFTER running the containers
- `@JvmStatic` -- To simulate the Java "static" method in Kotlin, for example `@DynamicPropertySource` should use a static method
- `@Container` -- It can be static/non-static, to reuse the container between tests we can set it as static

See: https://spring.io/blog/2023/06/23/improved-testcontainers-support-in-spring-boot-3-1
- `@ServiceConnection` -- It will autoset the spring properties so there is no need to use `@DynamicPropertySource`
- `fromApplication<DemoApplication>().with(TestDemoApplication::class).run(*args)` -- It will start your app USING testcontainer, ergo you can run your application without the need of the external services

See: https://java.testcontainers.org/modules/databases/jdbc/
- Database containers launched via JDBC URL scheme: Insert `tc:` after `jdbc:` as: `spring.datasource.url=jdbc:tc:mysql:8.0.32:///db`
