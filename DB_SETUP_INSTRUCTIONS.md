# Database Setup Instructions for XPerienceServerDB

This guide provides detailed steps to set up the MySQL database for the XPerienceServerDB application.

## Prerequisites

- MySQL installed and running
- Root access to MySQL or a user with privileges to create databases and users

## Step 1: Verify MySQL is running

First, make sure MySQL is running on your system:

```bash
# For systems using systemd
systemctl status mysql

# For other systems
service mysql status
```

If MySQL is not running, start it:

```bash
# For systems using systemd
sudo systemctl start mysql

# For other systems
sudo service mysql start
```

## Step 2: Set up the database

We've provided a simplified setup script that will:
1. Create the xperience_db database
2. Create the Event table
3. Insert a sample event
4. Create the xperienceuser account with appropriate permissions

Run:

```bash
./run-database-setup.sh
```

You will be prompted for your MySQL root password.

## Step 3: Test the database connection

To verify that the database is set up correctly:

```bash
./test-db-connection.sh
```

This will test connecting to the database using the xperienceuser account.

## Manual Setup (if needed)

If the scripts don't work, you can set up the database manually:

1. Log in to MySQL as root:
   ```bash
   mysql -u root -p
   ```

2. Execute these SQL commands:
   ```sql
   CREATE DATABASE IF NOT EXISTS xperience_db;
   
   USE xperience_db;
   
   -- Create the Event table
   DROP TABLE IF EXISTS Event;
   CREATE TABLE Event (
     name VARCHAR(255) NOT NULL,
     date VARCHAR(50),
     time VARCHAR(50),
     description VARCHAR(255),
     PRIMARY KEY (name)
   );
   
   -- Insert sample event
   INSERT INTO Event (name, date, time, description)
   VALUES ('SampleEvent', '01/01/2025', '12:00', 'A sample row created by setup script');
   
   -- Create user with proper permissions
   CREATE USER IF NOT EXISTS 'xperienceuser'@'localhost' IDENTIFIED BY 'XperiencePass123!';
   GRANT ALL PRIVILEGES ON xperience_db.* TO 'xperienceuser'@'localhost';
   FLUSH PRIVILEGES;
   
   -- Verify
   SELECT * FROM Event;
   ```

## Running the Database-Backed Server

Once the database is set up, run the database-backed server:

```bash
# On Linux/Mac
./run-server-db.sh

# On Windows
windows-run-server-db.bat
```

## Testing with the Client

To test the database-backed server:

```bash
# On Linux/Mac
./run-test-client.sh 8081

# On Windows
windows-run-test-client.bat 8081
```

## Troubleshooting

1. **Permission denied**: Make sure the xperienceuser account has proper permissions to the xperience_db database.

2. **Connection refused**: Ensure MySQL is running and accepting connections on localhost:3306.

3. **Unknown database**: Verify that the xperience_db database exists.

4. **Authentication failed**: Check that the password for xperienceuser is correctly set to "XperiencePass123!".

5. **Dependency issues**: Make sure the MySQL JDBC driver and DonaBase JAR files are in the classpath.

If issues persist, run `./debug-db.sh` for additional diagnostics.