create table rating
(
	rating_id INT NOT NULL auto_increment,
	user_id INT null,
	store_id INT null,
	rating int null,
		primary key (rating_id)
)default character set utf8 collate UTF8_GENERAL_CI;

