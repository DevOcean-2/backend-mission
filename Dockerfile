# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the build.gradle and settings.gradle files
COPY build.gradle settings.gradle /app/

# Copy the gradle wrapper files
COPY gradlew /app/gradlew
COPY gradle /app/gradle

# Grant execution permission to gradlew
RUN chmod +x /app/gradlew

# Download the dependencies
RUN /app/gradlew dependencies --no-daemon

# Copy the rest of the application code
COPY . /app

# Grant execution permission to gradlew again after copying the rest of the application code
RUN chmod +x /app/gradlew

# Build the application without running tests
RUN /app/gradlew build --no-daemon --no-build-cache -x test

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/build/libs/Balbalm-0.0.1-SNAPSHOT.jar"]