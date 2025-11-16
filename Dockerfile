FROM eclipse-temurin:17-jdk-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy only the files needed for dependency resolution first (better layer caching)
COPY mvnw .
RUN chmod +x mvnw # Make mvnw executable
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src src

# Build the application (jar file)
RUN ./mvnw clean package -DskipTests

# Final runtime stage
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="youssef.ammari.795@gmail.com"

# Create user to run app as non-root (improved syntax)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Create directory and set ownership
RUN mkdir -p /app && chown appuser:appgroup /app
WORKDIR /app

# Copy the jar from the builder stage (not from host's target directory)
COPY --from=builder --chown=appuser:appgroup /app/target/gridpulse-cloud-backend-0.0.1-SNAPSHOT.jar app.jar

# Switch to non-root user
USER appuser

# Expose the port the app runs on
EXPOSE 8080

# Use shell form for entrypoint to allow environment variable expansion
ENTRYPOINT ["java", "-jar", "/app/app.jar"]