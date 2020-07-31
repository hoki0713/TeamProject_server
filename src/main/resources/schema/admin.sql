CREATE TABLE admin
(
admin_id NOT NULL AUTO_INCREMENT,
industry_code INT NULL ,
amount_used INT NULL,
city VARCHAR(20) NULL,
classify_industry VARCHAR(20) NULL,
PRIMARY KEY (industry_code)
)default character set utf8 collate UTF8_GENERAL_CI;