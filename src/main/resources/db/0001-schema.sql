-- liquibase formatted sql
-- changeSet schema failOnError:true

CREATE TABLE user
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(64) NOT NULL,
    last_name  VARCHAR(64) NOT NULL,
    logo_color VARCHAR(7)  NOT NULL DEFAULT '#000000'
);

CREATE TABLE message
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    message    LONGTEXT NOT NULL,
    creator_id BIGINT   NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (creator_id) REFERENCES user (id)
);

CREATE TABLE hashtag
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    message_id BIGINT        NOT NULL,
    tag    varchar(1024) NOT NULL,
    FOREIGN KEY (message_id) REFERENCES message (id) ON DELETE CASCADE,
    INDEX (tag(32))
);