CREATE TABLE `favorites` (
                        `id` INT(11) NOT NULL AUTO_INCREMENT,
                        `user_id` INT,
                        `store_id` INT,
                        PRIMARY KEY (`id`)
)DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;