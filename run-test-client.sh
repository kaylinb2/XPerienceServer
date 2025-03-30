#!/bin/bash
# Run test client
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <port>"
    echo "Example: $0 8080   # Connect to in-memory server"
    echo "Example: $0 8081   # Connect to database server"
    exit 1
fi

PORT=$1
echo "Starting XPerienceTestClient connecting to localhost:$PORT..."
java -jar target/xperience-test-client-jar-with-dependencies.jar localhost $PORT