DROP SCHEMA IF EXISTS service CASCADE;
CREATE SCHEMA IF NOT EXISTS service;

CREATE TABLE service.users(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    password varchar(50),
    email varchar(50) UNIQUE
    );
