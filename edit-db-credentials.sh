#!/bin/bash

echo "=== Edit Database Credentials ==="
echo "This script will update the database credentials for XPerienceServerDB."

# Read current values from config file
if [ -f db-credentials.conf ]; then
    HOST=$(grep "^host=" db-credentials.conf | cut -d= -f2)
    PORT=$(grep "^port=" db-credentials.conf | cut -d= -f2)
    DATABASE=$(grep "^database=" db-credentials.conf | cut -d= -f2)
    USER=$(grep "^user=" db-credentials.conf | cut -d= -f2)
    PASSWORD=$(grep "^password=" db-credentials.conf | cut -d= -f2)
else
    # Default values
    HOST="localhost"
    PORT="3306"
    DATABASE="xperience_db"
    USER="xperienceuser"
    PASSWORD="XperiencePass123!"
fi

# Prompt for new values
read -p "Database host [$HOST]: " NEW_HOST
read -p "Database port [$PORT]: " NEW_PORT
read -p "Database name [$DATABASE]: " NEW_DATABASE
read -p "Database user [$USER]: " NEW_USER
read -p "Database password [$PASSWORD]: " NEW_PASSWORD

# Use default value if no input
NEW_HOST=${NEW_HOST:-$HOST}
NEW_PORT=${NEW_PORT:-$PORT}
NEW_DATABASE=${NEW_DATABASE:-$DATABASE}
NEW_USER=${NEW_USER:-$USER}
NEW_PASSWORD=${NEW_PASSWORD:-$PASSWORD}

# Save to config file
echo "host=$NEW_HOST" > db-credentials.conf
echo "port=$NEW_PORT" >> db-credentials.conf
echo "database=$NEW_DATABASE" >> db-credentials.conf
echo "user=$NEW_USER" >> db-credentials.conf
echo "password=$NEW_PASSWORD" >> db-credentials.conf

echo "Database credentials updated successfully!"
echo
echo "After setting up the database, use:"
echo "./run-server-db.sh"