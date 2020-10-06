CREATE TABLE `boards` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `deleted` boolean DEFAULT false,
    `description` varchar(500) NOT NULL,
    `name` varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
);