# ===========================
# STAGE 1 — BUILD
# ===========================
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy only the files needed for dependency resolution first (better layer caching)
COPY mvnw .
RUN chmod +x mvnw # Make mvn wrapper executable
COPY .mvn .mvn
COPY pom.xml .

# Pre-download maven dependencies in advance (caching optimization)
RUN ./mvnw -q dependency:go-offline

# Copy the source code (Avoiding cache break in each code updates)
COPY src src

# Build the application (jar file - skip tests)
RUN ./mvnw clean package -DskipTests

# ===========================
# STAGE 2 — RUNTIME
# ===========================
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="youssef.ammari.795@gmail.com"

# Create user to run app as non-root (improved syntax)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Create directory and set ownership
RUN mkdir -p /app && chown appuser:appgroup /app
WORKDIR /app

# Copy the jar from the builder stage (not from host's target directory)
COPY --from=builder --chown=appuser:appgroup /app/target/*.jar app.jar

# Switch to non-root user
USER appuser

# Expose the port the app runs on
EXPOSE 8080

# Use shell form to allow environment variable expansion
ENTRYPOINT sh -c "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"