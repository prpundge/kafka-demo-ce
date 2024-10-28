# Use the official Maven image as a builder
FROM maven:3.9.8-eclipse-temurin-21 AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven POM file and the source code
COPY pom.xml .
COPY src ./src

# Build the application (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Second stage: create a smaller image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/kafka-demo-ce-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (default Spring Boot port)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]