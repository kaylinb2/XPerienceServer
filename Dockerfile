# Use a base image with Java
FROM openjdk:17

# Copy the built JAR into the container
COPY target/xperience-server-db-jar-with-dependencies.jar /app/xperience-server.jar

# Set the working directory
WORKDIR /app

# Run the application
CMD ["java", "-jar", "xperience-server.jar", "8081", "localhost", "xperience/passwords.txt"]
