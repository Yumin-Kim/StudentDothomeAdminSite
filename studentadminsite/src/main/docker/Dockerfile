FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT java  -DBUILD=prod -jar ./app.jar
