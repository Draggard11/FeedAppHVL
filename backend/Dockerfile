########################
# 1. Build stage
########################
FROM gradle:8.14.3-alpine AS builder

# Work inside the project directory in the container
WORKDIR /home/gradle/project

# Copy Gradle build files and wrapper
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew gradlew

# Make sure the wrapper is executable (on Linux)
RUN chmod +x gradlew

# Copy the source code
COPY src src

# Build the Spring Boot fat jar
RUN ./gradlew clean bootJar --no-daemon

########################
# 2. Runtime stage
########################
FROM eclipse-temurin:21-jre-alpine

# Create a non-root user
RUN addgroup -S app && adduser -S app -G app

USER app
WORKDIR /app

# Copy the built jar from the builder image
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# Expose the port Spring Boot uses (optional but nice)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
