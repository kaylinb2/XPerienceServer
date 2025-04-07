FROM openjdk:21-slim
COPY target/xperience-project-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
COPY passwords.txt passwords.txt

# Hardcode the port and password file
ENTRYPOINT ["java", "-jar", "app.jar", "9020", "passwords.txt"]
