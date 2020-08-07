CREATE TABLE local_currency_voucher
(
    `local_currency_voucher_id`  INT            NOT NULL    AUTO_INCREMENT,
    `local_name`          VARCHAR(10)    NULL,
    `local_currency_name` VARCHAR(10)    NULL,
    `voucher_value` INT,
    PRIMARY KEY (local_currency_voucher_id)
)default character set utf8 collate UTF8_GENERAL_CI;