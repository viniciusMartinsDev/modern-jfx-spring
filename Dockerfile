FROM openjdk:21-jdk-slim AS build
WORKDIR /app
VOLUME /tmp
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY checkstyle.xml .
COPY src ./src
RUN chmod +x ./mvnw
RUN ./mvnw package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar my-spring-app.jar
ENTRYPOINT ["java","-jar","/app/my-spring-app.jar"]
