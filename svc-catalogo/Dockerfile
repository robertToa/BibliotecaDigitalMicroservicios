FROM maven:3.9-eclipse-temurin-17-alpine
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
EXPOSE 8081
CMD ["java", "-jar", "target/svc-catalogo-0.0.1-SNAPSHOT.jar"]