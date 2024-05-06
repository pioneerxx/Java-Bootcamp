DROP SCHEMA IF EXISTS service CASCADE;
CREATE SCHEMA IF NOT EXISTS service;

CREATE TABLE service.users(
    id SERIAL PRIMARY KEY,
    password text,
    email varchar(30) UNIQUE
    );

CREATE TABLE service.messages(
    id SERIAL PRIMARY KEY,
    sender INT,
    text text,
    date timestamp default CURRENT_TIMESTAMP,
    FOREIGN KEY (sender) REFERENCES service.users(id)
    );