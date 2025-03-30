# XPerienceServer - Final Instructions

## Quick Start Guide

This guide will help you run the XPerienceServer project with minimal setup.

### Step 1: Compile the Project

```bash
# First compile the project
mvn clean package
```

### Step 2: Choose Your Server Version

#### Option A: Run In-Memory Server (Recommended)

The in-memory server requires no database setup and works out of the box:

```bash
# Run the in-memory server
./run-server-inmemory.sh
```

#### Option B: Run Database Server (If MySQL is Available)

To run the database-backed server, you have two options:

1. **If you have MySQL installed and know the root password**:
   ```bash
   # Set up the database
   ./run-database-setup.sh
   
   # Run the database server
   ./run-server-db.sh
   ```

2. **If you don't know MySQL password or don't have MySQL**:
   ```bash
   # Edit the database credentials
   ./edit-db-credentials.sh
   
   # Run the server (will use default credentials)
   ./run-server-db.sh
   ```

### Step 3: Test with the Client

Once a server is running, test it with the client:

```bash
# Test in-memory server (port 8080)
./run-test-client.sh 8080

# Or test database server (port 8081)
./run-test-client.sh 8081
```

## Windows Instructions

For Windows users, use the provided BAT files:

```
windows-run-server-inmemory.bat
windows-run-server-db.bat
windows-run-test-client.bat 8080
```

## Detailed Documentation

- For in-depth instructions, see `XPerienceServer_User_Guide.md`
- For database setup details, see `DB_SETUP_INSTRUCTIONS.md`
- For running instructions, see `RUNNING_INSTRUCTIONS.md`

## Troubleshooting

If you encounter issues with the database server, the in-memory server should still work correctly as a fallback.

To debug database issues:
```bash
./debug-db.sh
```

## Commands Quick Reference

```bash
# Compile
mvn clean package

# Run in-memory server
./run-server-inmemory.sh

# Run database server
./run-server-db.sh

# Test client
./run-test-client.sh 8080
```