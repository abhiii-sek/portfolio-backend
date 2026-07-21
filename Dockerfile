# Build stage
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copy gradle wrapper and configuration files
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Grant execute permission for the gradle wrapper
RUN chmod +x gradlew

# Download dependencies (this layer is cached if dependencies don't change)
RUN ./gradlew dependencies --no-daemon || true

# Copy source code
COPY src src

# Build the executable bootJar, disabling tests since we don't have database connection during build
RUN ./gradlew bootJar -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/backend-1.0.0.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
