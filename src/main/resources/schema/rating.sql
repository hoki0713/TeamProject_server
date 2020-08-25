CREATE TABLE rating
(
	rating_id INT NOT NULL auto_increment,
	user_id LONG NULL,
	store_id LONG NULL,
	star_rating FLOAT NULL,
		PRIMARY KEY (rating_id)
)DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;