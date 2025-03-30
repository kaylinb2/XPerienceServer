#!/bin/bash

echo "=== Setting up XPerienceServer database ==="
echo "This simplified script will create the necessary database."
echo "You will need MySQL installed and running."

# Check if mysql command is available
if ! command -v mysql &> /dev/null; then
    echo "MySQL command not found. Please install MySQL first."
    exit 1
fi

# Prompt for MySQL username and password
read -p "Enter MySQL username (with sufficient privileges): " MYSQL_USER
echo -n "Enter MySQL password: "
read -s MYSQL_PASSWORD
echo

# Set up the database
echo "Creating database and setting up permissions..."
mysql -u "$MYSQL_USER" -p"${MYSQL_PASSWORD}" < simplified-db-setup.sql

if [ $? -eq 0 ]; then
    echo "Database setup completed successfully!"
    echo 
    echo "You can now run the database server with:"
    echo "./run-server-db.sh"
    echo
    echo "And test with:"
    echo "./run-test-client.sh 8081"
else
    echo "Failed to set up database. Check your MySQL connection and password."
fi