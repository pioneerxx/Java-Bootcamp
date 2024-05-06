DROP SCHEMA IF EXISTS service CASCADE;
CREATE SCHEMA IF NOT EXISTS service;

CREATE TABLE service.users(
    id SERIAL PRIMARY KEY,
    password text,
    email varchar(30) UNIQUE
    );