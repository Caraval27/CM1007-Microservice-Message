FROM openjdk:17-jdk-alpine
WORKDIR /journal_app
EXPOSE 8081

ARG JAR_FILE=Lab3_Message-0.0.1-SNAPSHOT.jar
ARG DEPENDENCY_JAR_FILE=Lab3_Message-0.0.1-SNAPSHOT-jar-with-dependencies.jar

COPY ${JAR_FILE} /journal_app/
COPY ${DEPENDENCY_JAR_FILE} /journal_app/

ENV SPRING_DATASOURCE_PASSWORD=admin
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_URL=jdbc:mysql://journal-app-db:3306/DB_Journal

CMD ["sh", "-c", "java -jar /journal_app/Lab3_Message-0.0.1-SNAPSHOT.jar && java -jar /journal_app/Lab3_Message-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
