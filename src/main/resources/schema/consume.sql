CREATE TABLE `consume` (
	`consume_id`	int	NOT NULL AUTO_INCREMENT,
	`gender_code`	varchar(2)	NULL,
	`age_group` int NULL,
	`industry_name`	varchar(15)	NULL,
	`amount`	bigint	NULL,
	primary key (consume_id)
) default character set utf8 collate UTF8_GENERAL_CI;