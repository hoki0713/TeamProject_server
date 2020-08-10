CREATE TABLE `store` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`store_name` VARCHAR(200),
		`store_type_code` INT,
	`main_code` varchar(10),
	`store_type` VARCHAR(50),
	`local_name` VARCHAR(10),
	`road_address` VARCHAR(200),
	`store_phone` VARCHAR(15),
	`latitude` VARCHAR(20) ,
	`longitude` VARCHAR(20) ,
	`search_result_count` INT,
	PRIMARY KEY (`id`)
)DEFAULT CHARSET=UTF8;

수정

CREATE TABLE `store` (
                         `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                         `store_name` VARCHAR(200),
                         `store_type_code` INT,
                         `store_type` VARCHAR(50),
                         `local_name` VARCHAR(10),
                         `road_address` VARCHAR(200),
                         `store_phone` VARCHAR(15),
                         `latitude` VARCHAR(20) ,
                         `longitude` VARCHAR(20) ,
                         `search_result_count` INT,
                         PRIMARY KEY (`id`)
)DEFAULT CHARSET=UTF8;