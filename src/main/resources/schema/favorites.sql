CREATE TABLE `favorites` (
                        `id` INT(11) NOT NULL AUTO_INCREMENT,
                        `user_id` int,
                        `store_id` int,
                        PRIMARY KEY (`id`)
)DEFAULT CHARSET=UTF8;