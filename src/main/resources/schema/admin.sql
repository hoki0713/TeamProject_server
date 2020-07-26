CREATE TABLE admin
(
industry_code INT NOT NULL AUTO_INCREMENT,
amount_used INT NULL,
city VARCHAR(20) NULL,
store_type VARCHAR(10) NULL,
PRIMARY KEY (industry_code)
)default character set utf8 collate UTF8_GENERAL_CI;