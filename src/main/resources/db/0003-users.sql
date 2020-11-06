-- liquibase formatted sql
-- changeSet users failOnError:true

ALTER TABLE user
ADD auth_id varchar(36),
ADD INDEX (auth_id);
