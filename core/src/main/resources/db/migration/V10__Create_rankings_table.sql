CREATE TABLE `rankings` (
    `id` bigint(20) NOT NULL,
    `created_date` datetime DEFAULT NULL,
    `modified_date` datetime DEFAULT NULL,
    `rank` int(11) NOT NULL,
    `score` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `Account_FK` FOREIGN KEY (`id`) REFERENCES `accounts` (`id`)
);