-- liquibase formatted sql
-- changeSet schema failOnError:true

CREATE TABLE User
(
    Id          BIGINT         PRIMARY KEY AUTO_INCREMENT,
    FirstName   VARCHAR(64)    NOT NULL,
    LastName    VARCHAR(64)    NOT NULL,
    LogoColor   VARCHAR(7)     NOT NULL DEFAULT '#000000'
);

CREATE TABLE Message
(
    Id          BIGINT      PRIMARY KEY AUTO_INCREMENT,
    Message     LONGTEXT    NOT NULL,
    CreatorId   BIGINT      NOT NULL,
    CreatedAt   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CreatorId) REFERENCES User(Id)
);