CREATE DATABASE IF NOT EXISTS xperience_db;
USE xperience_db;

CREATE TABLE IF NOT EXISTS Event (
    name VARCHAR(255) PRIMARY KEY,
    date VARCHAR(50),
    time VARCHAR(50),
    description TEXT
);

--  First, create or update the user separately
CREATE USER IF NOT EXISTS 'xperienceuser'@'localhost' IDENTIFIED BY 'XperiencePass123';
CREATE USER IF NOT EXISTS 'xperienceuser'@'%' IDENTIFIED BY 'XperiencePass123';

--  Then, grant privileges (WITHOUT IDENTIFIED BY)
GRANT ALL PRIVILEGES ON xperience_db.* TO 'xperienceuser'@'localhost';
GRANT ALL PRIVILEGES ON xperience_db.* TO 'xperienceuser'@'%';

FLUSH PRIVILEGES;
