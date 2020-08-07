CREATE TABLE voucher
(
voucher_id INT NOT NULL AUTO_INCREMENT,
local_currency_id INT NULL,
currency_type INT NULL,
PRIMARY KEY (voucher_id)
)default character set utf8 collate UTF8_GENERAL_CI;