FROM openjdk:17-jdk-alpine
WORKDIR /journal_app
EXPOSE 8081

COPY Lab3_Message-0.0.1-SNAPSHOT.jar /journal_app/
COPY Lab3_Message-0.0.1-SNAPSHOT-jar-with-dependencies.jar /journal_app/

ENV SPRING_DATASOURCE_PASSWORD=admin
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_URL=jdbc:mysql://journal-app-db:3306/DB_Journal

CMD ["sh", "-c", "java -jar /journal_app/Lab3_Message-0.0.1-SNAPSHOT.jar && java -jar /journal_app/Lab3_Message-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
