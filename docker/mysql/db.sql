CREATE DATABASE IF NOT EXISTS litebooth;
CREATE USER IF NOT EXISTS 'litebooth'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON litebooth.* TO 'litebooth'@'%';
FLUSH PRIVILEGES;

USE litebooth;

CREATE TABLE `user`
(
    `id`             BIGINT       NOT NULL AUTO_INCREMENT,
    `avatar_file_id` BIGINT       NOT NULL,
    `username`       VARCHAR(255) NOT NULL,
    `password`       VARCHAR(255) NOT NULL,
    `email`          VARCHAR(255) NOT NULL,
    `created_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`),
    UNIQUE KEY `idx_email` (`email`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `file`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(255) NOT NULL,
    `hash`       VARCHAR(255) NOT NULL,
    `size`       BIGINT       NOT NULL,
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `item`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `show`        BOOLEAN      NOT NULL,
    `price`       DOUBLE       NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `description` TEXT         NOT NULL,
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `home_info`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `announcement` TEXT         NOT NULL,
    `title`        VARCHAR(255) NOT NULL,
    `subtitle`     VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;
INSERT INTO `home_info` (`id`, `announcement`, `title`, `subtitle`)
VALUES (0, '', '标题', '副标题');

CREATE TABLE `group`
(
    `id`   BIGINT       NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;
INSERT INTO `group` (`id`, `name`) VALUES (0, '全部');