# Use adoptopenjdk for base image
FROM adoptopenjdk:11-jre-hotspot AS base

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY build/libs/cheap9-0.0.1-SNAPSHOT.war app.war

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.war"]