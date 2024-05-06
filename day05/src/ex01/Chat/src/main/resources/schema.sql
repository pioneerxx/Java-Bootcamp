DROP SCHEMA IF EXISTS Chat CASCADE;
CREATE SCHEMA IF NOT EXISTS Chat;

CREATE TABLE IF NOT EXISTS Chat.Users (
    Id SERIAL PRIMARY KEY,
    Login varchar(30) NOT NULL UNIQUE,
    Password varchar(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS Chat.Chatrooms (
    Id SERIAL PRIMARY KEY,
    Name varchar(30) NOT NULL,
    Owner INT NOT NULL,
    FOREIGN KEY (Owner) REFERENCES Chat.Users(Id)
);

CREATE TABLE IF NOT EXISTS Chat.Messages (
    Id SERIAL PRIMARY KEY,
    Author INT NOT NULL,
    Room INT NOT NULL,
    Text text NOT NULL,
    Date timestamp default CURRENT_TIMESTAMP,
    FOREIGN KEY (Author) REFERENCES Chat.Users(Id),
    FOREIGN KEY (Room) REFERENCES Chat.Chatrooms(Id)
);
