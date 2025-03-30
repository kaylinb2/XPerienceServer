# XPerienceServer User Guide

This guide provides step-by-step instructions for compiling and running the XPerienceServer application on Ubuntu.

## Project Overview

The XPerienceServer is a multi-threaded server that accepts event submissions from clients. It comes in two versions:

1. **In-Memory Server**: Stores events in memory during the server's lifetime.
2. **Database-Backed Server**: Stores events in a MySQL database using DonaBase library.

## Prerequisites

Ensure you have the following installed on your Ubuntu system:

- Java Development Kit (JDK) 11 or higher
- Maven (for building the project)
- MySQL Server (only required for database-backed server)

### Installing Prerequisites

```bash
# Update package list
sudo apt update

# Install OpenJDK 11
sudo apt install openjdk-11-jdk

# Install Maven
sudo apt install maven

# Install MySQL (if needed for database server)
sudo apt install mysql-server
```

## Setting Up the Project

1. Extract the project files to a directory of your choice.

2. Open a terminal and navigate to the project directory:
   ```bash
   cd /path/to/xperience-server
   ```

3. Verify that the following files are present:
   - Java source files: `Event.java`, `XPerienceServer.java`, `XPerienceServerDB.java`, `XPerienceTestClient.java`
   - JAR files: `DonaBase.jar`, `MySQLJDBC.jar`
   - SQL file: `event.sql`
   - Maven configuration: `pom.xml`

## Compiling the Project

Use Maven to compile and package the project:

```bash
# Clean and package the project
mvn clean package
```

This will create the following JAR files in the `target` directory:
- `xperience-server-jar-with-dependencies.jar` (In-Memory Server)
- `xperience-server-db-jar-with-dependencies.jar` (Database-Backed Server)
- `xperience-test-client-jar-with-dependencies.jar` (Test Client)

## Database Setup (for Database-Backed Server)

Before running the database-backed server, you need to set up the MySQL database:

1. Start the MySQL service:
   ```bash
   sudo systemctl start mysql
   ```

2. Log in to MySQL as root:
   ```bash
   sudo mysql -u root -p
   ```

3. Create the database by importing the SQL file:
   ```sql
   source event.sql
   ```

4. Verify that the database and table were created:
   ```sql
   USE xperience_db;
   SHOW TABLES;
   SELECT * FROM Event;
   ```

5. Verify user permissions:
   ```sql
   SHOW GRANTS FOR 'xperienceuser'@'%';
   ```

6. Exit MySQL:
   ```sql
   EXIT;
   ```

7. (Optional) If you need to modify the database connection settings, edit the `XPerienceServerDB.java` file and recompile:
   ```java
   private static String dbServer = "localhost"; // Default: 192.168.221.179
   private static int dbPort = 3306;
   private static String dbName = "xperience_db";
   private static String dbUser = "xperienceuser";
   private static String dbPass = "XperiencePass123!";
   ```

## Running the In-Memory Server

To run the in-memory version of the XPerienceServer:

```bash
java -jar target/xperience-server-jar-with-dependencies.jar 8080
```

Replace `8080` with your desired port number.

The server will start and display a message indicating it's running on the specified port. It will create a log file `xperience_inmemory.log` in the current directory.

## Running the Database-Backed Server

To run the database-backed version of the XPerienceServer:

```bash
java -jar target/xperience-server-db-jar-with-dependencies.jar 8081
```

Replace `8081` with your desired port number (use a different port than the in-memory server if you want to run both simultaneously).

The server will start and display a message indicating it's running on the specified port. It will create a log file `xperience_db.log` in the current directory.

## Running the Test Client

Once you have either server running, you can test it using the XPerienceTestClient:

```bash
java -jar target/xperience-test-client-jar-with-dependencies.jar localhost 8080
```

Replace `localhost` with the server host if it's running on a different machine, and `8080` with the port number the server is running on.

The client will send several test event submissions to the server and display the responses. Some events are designed to be rejected due to duplicate names or invalid format.

## Troubleshooting

1. **Connection refused**:
   - Make sure the server is running on the specified port
   - Check if a firewall is blocking the connection
   - Verify that the host and port are correct in the client command

2. **Database connection issues**:
   - Verify MySQL is running: `systemctl status mysql`
   - Check database credentials in `XPerienceServerDB.java`
   - Ensure the user has appropriate permissions
   - Try connecting manually to test: `mysql -u xperienceuser -p xperience_db`

3. **Java errors**:
   - Ensure you have Java 11 or higher: `java -version`
   - Make sure Maven is properly installed: `mvn -version`

4. **Log files**:
   - Check `xperience_inmemory.log` or `xperience_db.log` for server errors
   - Turn on verbose output for Maven if build fails: `mvn -X clean package`

## Additional Information

- Both servers implement the same protocol but use different storage mechanisms
- All events require a unique name to be accepted
- The servers run until manually terminated (Ctrl+C)
- Use log files to monitor server activity and troubleshoot issues