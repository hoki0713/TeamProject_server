CREATE TABLE voucher
(
voucher_no INT NOT NULL AUTO_INCREMENT,
local_currency VARCHAR(10) NULL,
currency_type INT NULL,
PRIMARY KEY (voucher_no)
)default character set utf8 collate UTF8_GENERAL_CI;