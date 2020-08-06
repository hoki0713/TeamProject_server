CREATE TABLE `consume` (
	`consume_id`	int	NOT NULL AUTO_INCREMENT,
	`month`	varchar(15)	NULL,
	`city`	varchar(10)	NULL,
	`gender_code`	varchar(2)	NULL,
	`industry_name`	varchar(15)	NULL,
	`amount`	int	NULL,
	primary key (consume_id)
) default character set utf8 collate UTF8_GENERAL_CI;