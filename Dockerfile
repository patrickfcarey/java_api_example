# Stage 1: Build the app with Maven and OpenJDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy Maven POM and resolve dependencies (cache layer)
COPY pom.xml .
RUN mvn dependency:resolve

# Copy source code and build
COPY src ./src
RUN mvn clean package

# Stage 2: Run the app with OpenJDK 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/myapp-1.0-SNAPSHOT.jar .
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "myapp-1.0-SNAPSHOT.jar"]

