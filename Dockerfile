FROM openjdk:11-jdk-slim
RUN mkdir app
COPY build/libs/*-all.jar app/app.jar
ENTRYPOINT java -jar app/app.jar
