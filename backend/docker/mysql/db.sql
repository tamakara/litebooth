CREATE DATABASE IF NOT EXISTS litebooth;
CREATE USER IF NOT EXISTS 'litebooth'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON litebooth.* TO 'litebooth'@'%';
FLUSH PRIVILEGES;

USE litebooth;

CREATE TABLE `file`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(255) NOT NULL,
    `hash`       VARCHAR(255) NOT NULL,
    `size`       BIGINT       NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `shop_info`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT,
    `logo_file_id`      BIGINT       NOT NULL,
    `logo_title`        VARCHAR(255) NOT NULL,
    `home_title`        VARCHAR(255) NOT NULL,
    `home_subtitle`     VARCHAR(255) NOT NULL,
    `home_announcement` TEXT         NOT NULL,
    `created_at`        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;
INSERT INTO `shop_info` (`id`, `logo_file_id`, `logo_title`, `home_title`, `home_subtitle`, `home_announcement`)
VALUES (1, 1, 'LiteBooth', '', '', '');

CREATE TABLE `item`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT,
    `is_active`     BOOLEAN      NOT NULL,
    `name`          VARCHAR(255) NOT NULL,
    `price`         DOUBLE       NOT NULL,
    `group`         VARCHAR(255) NOT NULL,
    `cover_file_id` BIGINT       NULL,
    `description`   TEXT         NOT NULL,
    `created_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;
INSERT INTO `item` (`id`, `is_active`, `name`, `price`, `group`, `cover_file_id`, `description`)
VALUES (1, true, '示例商品 1', 1.11, '1', 2, 'a');
INSERT INTO `item` (`id`, `is_active`, `name`, `price`, `group`, `cover_file_id`, `description`)
VALUES (2, true, '示例商品 2', 2.22, '2', 2, 'b');
INSERT INTO `item` (`id`, `is_active`, `name`, `price`, `group`, `cover_file_id`, `description`)
VALUES (3, true, '示例商品 3', 3.33, '3', 2, 'c');

CREATE TABLE `order`
(
    `id`             BIGINT       NOT NULL,
    `status`         VARCHAR(20)  NOT NULL,
    `query_email`    VARCHAR(255) NOT NULL,
    `query_password` VARCHAR(255) NOT NULL,
    `item_id`        BIGINT       NOT NULL,
    `item_name`      VARCHAR(255) NOT NULL,
    `item_price`     DOUBLE       NOT NULL,
    `quantity`       BIGINT       NOT NULL,
    `amount`         DOUBLE       NOT NULL,
    `payment_method` VARCHAR(20)  NOT NULL,
    `created_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `payment_at`     TIMESTAMP DEFAULT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `group`
(
    `id`   BIGINT       NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;
INSERT INTO `group` (`id`, `name`)
VALUES (0, '全部');

CREATE TABLE `stock`
(
    `id`         BIGINT  NOT NULL,
    `is_sold`    BOOLEAN NOT NULL,
    `item_id`    BIGINT  NOT NULL,
    `order_id`   BIGINT    DEFAULT NULL,
    `content`    TEXT    NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `admin_info`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `avatar`     BIGINT       NOT NULL,
    `username`   VARCHAR(255) NOT NULL,
    `password`   VARCHAR(255) NOT NULL,
    `nickname`   VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;
INSERT INTO `admin_info` (`id`, `avatar`, `username`, `password`, `nickname`)
VALUES (1, '0', 'admin', 'admin', 'admin');