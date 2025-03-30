@echo off
REM Run in-memory server on Windows
echo Starting XPerienceServer in-memory on port 8080...
java -cp "target\xperience-server-jar-with-dependencies.jar;DonaBase.jar;MySQLJDBC.jar" xperience.XPerienceServer 8080