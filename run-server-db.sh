#!/bin/bash
# Run database-backed server with credentials from config file

echo "Starting XPerienceServer database-backed on port 8081..."

# Check if config file exists
if [ ! -f db-credentials.conf ]; then
    echo "Database configuration file not found. Creating default configuration..."
    echo "host=localhost" > db-credentials.conf
    echo "port=3306" >> db-credentials.conf
    echo "database=xperience_db" >> db-credentials.conf
    echo "user=xperienceuser" >> db-credentials.conf
    echo "password=XperiencePass123!" >> db-credentials.conf
    echo "Default configuration created. Edit with ./edit-db-credentials.sh if needed."
fi

# Read credentials from config file
HOST=$(grep "^host=" db-credentials.conf | cut -d= -f2)
PORT=$(grep "^port=" db-credentials.conf | cut -d= -f2)
DATABASE=$(grep "^database=" db-credentials.conf | cut -d= -f2)
USER=$(grep "^user=" db-credentials.conf | cut -d= -f2)
PASSWORD=$(grep "^password=" db-credentials.conf | cut -d= -f2)

echo "Using database: $DATABASE on $HOST:$PORT"
echo "Starting server..."

# Create temp Java properties file for passing DB credentials
echo "db.host=$HOST" > db.properties
echo "db.port=$PORT" >> db.properties
echo "db.name=$DATABASE" >> db.properties
echo "db.user=$USER" >> db.properties
echo "db.pass=$PASSWORD" >> db.properties

# Run the server with the properties file
java -cp "target/xperience-server-db-jar-with-dependencies.jar:DonaBase.jar:MySQLJDBC.jar" \
    -Ddb.properties=db.properties \
    xperience.XPerienceServerDB 8081

# Clean up
rm -f db.properties