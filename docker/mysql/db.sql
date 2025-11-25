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
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `item`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT,
    `is_active`        BOOLEAN      NOT NULL,
    `is_auto_delivery` BOOLEAN      NOT NULL,
    `name`             VARCHAR(255) NOT NULL,
    `price`            DOUBLE       NOT NULL,
    `group`            VARCHAR(255) NOT NULL,
    `cover`            BIGINT       NULL,
    `description`      TEXT         NOT NULL,
    `created_at`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;
INSERT INTO `item` (`id`, `is_active`, `is_auto_delivery`, `name`, `price`, `stock`, `group`, `cover`, `description`)
VALUES (1, true, true, '示例商品 1', 1.11, 1, '1', 2, 'a');
INSERT INTO `item` (`id`, `is_active`, `is_auto_delivery`, `name`, `price`, `stock`, `group`, `cover`, `description`)
VALUES (2, true, false, '示例商品 2', 2.22, 2, '2', 2, 'b');
INSERT INTO `item` (`id`, `is_active`, `is_auto_delivery`, `name`, `price`, `stock`, `group`, `cover`, `description`)
VALUES (3, true, true, '示例商品 3', 3.33, 3, '3', 2, 'c');

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
INSERT INTO `group` (`id`, `name`)
VALUES (0, '全部');

CREATE TABLE `order`
(
    `id`          BIGINT       NOT NULL,
    `status`      VARCHAR(255) NOT NULL,
    `user_id`     BIGINT       NOT NULL,
    `user_mail`   VARCHAR(255) NOT NULL,
    `item_id`     BIGINT       NOT NULL,
    `item_name`   VARCHAR(255) NOT NULL,
    `item_price`  DOUBLE       NOT NULL,
    `quantity`    BIGINT       NOT NULL,
    `total_price` DOUBLE       NOT NULL,
    `pay_method`  VARCHAR(255) NOT NULL,
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `stock`
(
    `id`         BIGINT       NOT NULL,
    `status`     VARCHAR(255) NOT NULL,
    `item_id`    BIGINT       NOT NULL,
    `order_id`   BIGINT    DEFAULT NULL,
    `content`    TEXT         NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

