# Base image with Java 17 installed
FROM eclipse-temurin:17-jdk

# Set working directory inside container
WORKDIR /app

# Copy jar file from target folder into container
COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 8081

# Command to run application
ENTRYPOINT ["java", "-jar", "app.jar"]