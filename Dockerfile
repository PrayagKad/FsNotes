# Multi-stage build for FsNotes Backend
FROM openjdk:21-jdk-slim as builder

WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Production stage
FROM openjdk:21-jre-slim

WORKDIR /app

# Create logs directory
RUN mkdir -p logs

# Copy the built jar
COPY --from=builder /app/target/FsNotes-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV PORT=8080

# Run the application
CMD ["java", "-jar", "app.jar"]
