FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /build_journal_app
COPY Backend_Message/pom.xml .
COPY Backend_Message/src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /journal_app
EXPOSE 8081

COPY --from=build /build_journal_app/target/*.jar /journal_app/

ENV SPRING_DATASOURCE_PASSWORD=admin
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_URL=jdbc:mysql://journal-app-database.app.cloud.cbh.kth.se:3306/DB_Journal

CMD ["sh", "-c", "java -jar /journal_app/Backend_Message-0.0.1-SNAPSHOT.jar && java -jar /journal_app/Backend_Message-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]