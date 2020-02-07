# A Micronaut service template

Bootstrap the creation of a new service based on the [Micronaut](www.micronaut.io) framework.

This template provides the following features:

- Kotlin
- [Micronaut](www.micronaut.io) framework
- Gradle build
- Testing with JUnit 5, [Spek](https://spekframework.org) and [KotlinTest](https://github.com/kotlintest/kotlintest)
- Jacoco reports
- [gradle console reporter](https://github.com/ksoichiro/gradle-console-reporter) enabled
- Simple health endpoint
- Prometheus
- OpenApi annotation support ([more details](https://github.com/OAI/OpenAPI-Specification))
- Linting with [ktlint](https://ktlint.github.io/)
- Static code analysis with [detekt](https://arturbosch.github.io/detekt/)
- Simple CI pipeline
- Dockerfile
- [Google Jib](https://github.com/GoogleContainerTools/jib) (alternative way to build a Docker image)
- Swagger UI

## Quick Start

Chose between the following approaches to start the application server.
The server will be available on `http://localhost:8080`. 

You will see the `Swagger UI`.

### With Gradle

```
./gradlew run
```

### With Docker

```
./gradlew build
docker build -t micronaut-service-template .
docker run -it -p 8080:8080 micronaut-service-template:latest
```

## Open API

This project uses Open Api annotations to generate the Open Api spec. The generated spec is made available.
When running the template application the API is available at:

- http://localhost:8080/swagger/micronaut-service-template-0.1.yml

See the `micronaut.service.template.App` which is annotated with the `OpenAPIDefinition`.

The example REST endpoint is the famous `Hello World` example.

- http://localhost:8080/api/v1/hello

## Other Endpoints

Following endpoints are configured in the [application.properties](./blob/master/src/main/resources/application.yml).

##### The Prometheus exporter

- http://localhost:8080/prometheus

#####  The Health endpoint

- http://localhost:8080/health

## Usage

To customize this project and start development on it follow the steps below:

- Create a new project in GitHub
- Also clone this project
- Update the Git remote (execute `git remote set-url origin git@github.com:<user>/<new-project>.git`)
- Rename the project in its source code (execute `./rename-project.sh`)
- Commit and push to the new project

## More

- [Developer Setup](docs/developer-setup.md)
