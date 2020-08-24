CREATE TABLE `user` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`user_id` VARCHAR(30),
`password` VARCHAR(80),
`name` VARCHAR(10),
`birth_date` DATE,
`gender` VARCHAR(2),
`email` VARCHAR(100),
`join_date` DATE,
`withdraw_date` DATE,
`admin_key` INT(11),
`card_number` VARCHAR(50),
`default_addr` VARCHAR(100),
`optional_addr` VARCHAR(100),
PRIMARY KEY (`id`)
)DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;