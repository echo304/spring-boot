CREATE TABLE `account_role_assignments` (
  `account_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`account_id`,`role_id`)
);
