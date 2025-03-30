@echo off
REM Run test client on Windows
if "%1"=="" (
    echo Usage: %0 ^<port^>
    echo Example: %0 8080   # Connect to in-memory server
    echo Example: %0 8081   # Connect to database server
    exit /b
)

set PORT=%1
echo Starting XPerienceTestClient connecting to localhost:%PORT%...
java -jar target\xperience-test-client-jar-with-dependencies.jar localhost %PORT%