CREATE TABLE `consume` (
	`consume_id`	int	NOT NULL AUTO_INCREMENT,
	`gender_code`	VARCHAR(2)	NULL,
	`age_group` INT NULL,
	`industry_name`	VARCHAR(15)	NULL,
	`amount`	bigint	NULL,
	primary key (consume_id)
) DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;