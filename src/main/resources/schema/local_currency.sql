CREATE TABLE currency
(
    `currency_id`  INT            NOT NULL    AUTO_INCREMENT,
    `use_local`          VARCHAR(10)    NULL,
    PRIMARY KEY (local_currency_id)
)default character set utf8 collate UTF8_GENERAL_CI;