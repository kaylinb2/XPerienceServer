# XPerience Server Project

This project includes a multithreaded event management server with both in-memory and database-backed implementations.

## Project Components

- **In-Memory Server**: Stores events in memory during runtime
- **Database-Backed Server**: Stores events in a MySQL database 
- **Test Client**: A client application to test the server functionality

## Quick Start Guide

### Step 1: Compile the Project

```bash
# First compile the project
mvn clean package
```

### Step 2: Run the In-Memory Server

```bash
# On Linux/Mac
./run-server-inmemory.sh

# On Windows
windows-run-server-inmemory.bat
```

### Step 3: Test with the Client

```bash
# On Linux/Mac
./run-test-client.sh 8080

# On Windows
windows-run-test-client.bat 8080
```

## Running the Database Version

If you want to use the database-backed version, you'll need MySQL:

1. Edit database connection settings:
   ```bash
   ./edit-db-credentials.sh
   ```

2. Run the database server:
   ```bash
   ./run-server-db.sh
   ```

3. Test with the client:
   ```bash
   ./run-test-client.sh 8081
   ```

## Project Structure

- `src/main/java/xperience/` - Java source code
- `target/` - Compiled class files and JAR files
- `*.sh` and `*.bat` - Scripts for running the application
- `DonaBase.jar` - Database connection library
- `MySQLJDBC.jar` - MySQL JDBC driver
- `event.sql` - SQL script for database setup

## Additional Documentation

For more detailed information, refer to:
- `XPerienceServer_User_Guide.md` - Comprehensive user guide
- `DB_SETUP_INSTRUCTIONS.md` - Database setup instructions
- `RUNNING_INSTRUCTIONS.md` - Detailed running instructions
- `FINAL_INSTRUCTIONS.md` - Simplified quick-start guide