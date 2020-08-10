create table rating
(
	rating_id INT NOT NULL auto_increment,
	user_id long null,
	store_id long null,
	rating float null,
		primary key (rating_id)
)default character set utf8 collate UTF8_GENERAL_CI;

