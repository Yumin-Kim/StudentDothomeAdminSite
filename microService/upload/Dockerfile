FROM adoptopenjdk/openjdk11
COPY target/*.jar app.jar
ENTRYPOINT java -DBUILD=prod -jar app.jar