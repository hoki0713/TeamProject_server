CREATE TABLE `monthly` (
	`monthly_id`	int	NOT NULL AUTO_INCREMENT,
	`period`	varchar(10)	NULL,
	`industry_name`	varchar(15)	NULL,
	`amount`	BIGINT	NULL,
	primary key (monthly_id)
) default character set utf8 collate UTF8_GENERAL_CI;