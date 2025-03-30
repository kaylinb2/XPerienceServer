-- Drop and create database
DROP DATABASE IF EXISTS xperience_db;
CREATE DATABASE xperience_db;

USE xperience_db;

-- Create the Event table
-- We use "name" as a unique column
CREATE TABLE Event (
  name        VARCHAR(255) NOT NULL,
  date        VARCHAR(50),
  time        VARCHAR(50),
  description VARCHAR(255),
  PRIMARY KEY (name)
);

-- Insert at least one sample row
INSERT INTO Event (name, date, time, description)
VALUES ('SampleEvent', '01/01/2025', '12:00', 'A sample row created by event.sql');

-- Create users
CREATE USER IF NOT EXISTS 'xperienceuser'@'%' IDENTIFIED BY 'XperiencePass123!';
CREATE USER IF NOT EXISTS 'xperienceuser'@'localhost' IDENTIFIED BY 'XperiencePass123!';

-- Grant privileges to the users
GRANT ALL PRIVILEGES ON xperience_db.* TO 'xperienceuser'@'%';
GRANT ALL PRIVILEGES ON xperience_db.* TO 'xperienceuser'@'localhost';
FLUSH PRIVILEGES;
