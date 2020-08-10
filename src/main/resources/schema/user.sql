CREATE TABLE `user` (
                        `id` INT(11) NOT NULL AUTO_INCREMENT,
                        `user_id` VARCHAR(30) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
                        `password` VARCHAR(80) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
                        `name` VARCHAR(10) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
                        `birth_date` DATE NULL DEFAULT NULL,
                        `genderAge` VARCHAR(2) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
                        `email` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
                        `join_date` DATE NULL DEFAULT NULL,
                        `withdraw_date` DATE NULL DEFAULT '0000-00-00',
                        `admin_key` INT(11) NULL DEFAULT NULL,
                        `card_number` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
                        `default_addr` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
                        `optional_addr` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
                        PRIMARY KEY (`id`) USING BTREE,
                        UNIQUE INDEX `UKa3imlf41l37utmxiquukk8ajc` (`user_id`) USING BTREE
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

CREATE TABLE `user` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`user_id` VARCHAR(30),
`password` VARCHAR(80),
`name` VARCHAR(10),
`birth_date` DATE,
`genderAge` VARCHAR(2),
`email` VARCHAR(100),
`join_date` DATE,
`withdraw_date` DATE,
`admin_key` INT(11),
`card_number` VARCHAR(50),
`default_addr` VARCHAR(100),
`optional_addr` VARCHAR(100),
PRIMARY KEY (`id`)
)DEFAULT CHARSET=UTF8;
