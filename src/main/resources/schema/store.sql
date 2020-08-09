CREATE TABLE `store` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`store_name` VARCHAR(200),
	`main_code` varchar(10),
	`store_type` VARCHAR(50),
	`store_type_code` INT,
	`local_name` VARCHAR(10),
	`road_address` VARCHAR(200),
	`store_phone` VARCHAR(15),
	`latitude` VARCHAR(20) ,
	`longitude` VARCHAR(20) ,
	`star_ranking` INT,
	`search_result_count` INT,
	PRIMARY KEY (`id`)
)DEFAULT CHARSET=UTF8;