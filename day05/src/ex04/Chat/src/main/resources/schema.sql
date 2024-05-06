DROP SCHEMA IF EXISTS Chat CASCADE;
CREATE SCHEMA IF NOT EXISTS Chat;

CREATE TABLE IF NOT EXISTS Chat.Users (
    Id SERIAL PRIMARY KEY,
    Login varchar(30) UNIQUE,
    Password varchar(30)
);

CREATE TABLE IF NOT EXISTS Chat.Chatrooms (
    Id SERIAL PRIMARY KEY,
    Name varchar(30),
    Owner INT,
    FOREIGN KEY (Owner) REFERENCES Chat.Users(Id)
);

CREATE TABLE IF NOT EXISTS Chat.Messages (
    Id SERIAL PRIMARY KEY,
    Author INT,
    Room INT,
    Text text,
    Date timestamp default CURRENT_TIMESTAMP,
    FOREIGN KEY (Author) REFERENCES Chat.Users(Id),
    FOREIGN KEY (Room) REFERENCES Chat.Chatrooms(Id)
);
