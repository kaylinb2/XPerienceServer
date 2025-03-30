# Running the XPerienceServer Project

This guide will help you run the XPerienceServer project with step-by-step instructions.

## Prerequisite

Make sure you have:

1. Java 11 or higher installed
2. Maven installed (for compiling)
3. MySQL installed and running (for database-backed server only)

## Step 1: Compile the Project

```bash
# Navigate to the project directory
cd /path/to/xperience-server

# Compile with Maven
mvn clean package
```

## Step 2: Running the In-Memory Server

### On Linux/Mac:
```bash
# Use the provided script
./run-server-inmemory.sh

# Or run manually
java -cp "target/xperience-server-jar-with-dependencies.jar:DonaBase.jar:MySQLJDBC.jar" xperience.XPerienceServer 8080
```

### On Windows:
```cmd
# Use the provided batch file
windows-run-server-inmemory.bat

# Or run manually
java -cp "target\xperience-server-jar-with-dependencies.jar;DonaBase.jar;MySQLJDBC.jar" xperience.XPerienceServer 8080
```

## Step 3: Set Up Database (Only for Database-Backed Server)

```bash
# Import the SQL file into MySQL
mysql -u root -p < event.sql
```

## Step 4: Running the Database-Backed Server

### On Linux/Mac:
```bash
# Use the provided script
./run-server-db.sh

# Or run manually
java -cp "target/xperience-server-db-jar-with-dependencies.jar:DonaBase.jar:MySQLJDBC.jar" xperience.XPerienceServerDB 8081
```

### On Windows:
```cmd
# Use the provided batch file
windows-run-server-db.bat

# Or run manually
java -cp "target\xperience-server-db-jar-with-dependencies.jar;DonaBase.jar;MySQLJDBC.jar" xperience.XPerienceServerDB 8081
```

## Step 5: Testing with the Client

### On Linux/Mac:
```bash
# Test in-memory server
./run-test-client.sh 8080

# Test database server
./run-test-client.sh 8081

# Or run manually
java -jar target/xperience-test-client-jar-with-dependencies.jar localhost 8080
```

### On Windows:
```cmd
# Test in-memory server
windows-run-test-client.bat 8080

# Test database server
windows-run-test-client.bat 8081

# Or run manually
java -jar target\xperience-test-client-jar-with-dependencies.jar localhost 8080
```

## Expected Output

### In-Memory Server:
```
Starting XPerienceServer in-memory on port 8080...
INFO: XPerience Server (In-Memory) started on port 8080
```

### Database-Backed Server:
```
Starting XPerienceServer database-backed on port 8081...
INFO: XPerience Server (Database-Backed) started on port 8081
```

### Test Client:
```
Connected to XPerienceServer on localhost:8080
✅ Accepted Event: Danooke#02/12/2025#8pm#Fusion of Karaoke and Dance#
❌ Rejected Event: Danooke#02/14/2025#9pm#Fusion of Dance and Karaoke#
✅ Accepted Event: Mimey#02/14/2025#5pm#Poetry read by a mime#
...
```

## Troubleshooting

1. **ClassNotFoundException**:
   - Make sure to use the -cp option to include all dependencies

2. **Database Connection Issues**:
   - Verify MySQL is running
   - Check that the database, tables, and user are set up correctly
   - Ensure the database connection settings in XPerienceServerDB.java match your MySQL setup

3. **Connection Refused**:
   - Make sure the server is running on the specified port
   - Check for any firewall issues