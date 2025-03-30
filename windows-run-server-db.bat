@echo off
REM Run database-backed server on Windows with credentials from config file

echo Starting XPerienceServer database-backed on port 8081...

REM Check if config file exists
IF NOT EXIST db-credentials.conf (
    echo Database configuration file not found. Creating default configuration...
    echo host=localhost > db-credentials.conf
    echo port=3306 >> db-credentials.conf
    echo database=xperience_db >> db-credentials.conf
    echo user=xperienceuser >> db-credentials.conf
    echo password=XperiencePass123! >> db-credentials.conf
    echo Default configuration created. Edit settings if needed.
)

REM Read from config file (simple parsing)
FOR /F "tokens=1,2 delims==" %%G IN (db-credentials.conf) DO (
    IF "%%G"=="host" SET HOST=%%H
    IF "%%G"=="port" SET PORT=%%H
    IF "%%G"=="database" SET DATABASE=%%H
    IF "%%G"=="user" SET USER=%%H
    IF "%%G"=="password" SET PASSWORD=%%H
)

echo Using database: %DATABASE% on %HOST%:%PORT%
echo Starting server...

REM Create temp properties file
echo db.host=%HOST% > db.properties
echo db.port=%PORT% >> db.properties
echo db.name=%DATABASE% >> db.properties
echo db.user=%USER% >> db.properties
echo db.pass=%PASSWORD% >> db.properties

REM Run server with properties
java -cp "target\xperience-server-db-jar-with-dependencies.jar;DonaBase.jar;MySQLJDBC.jar" ^
    -Ddb.properties=db.properties ^
    xperience.XPerienceServerDB 8081

REM Clean up
del db.properties