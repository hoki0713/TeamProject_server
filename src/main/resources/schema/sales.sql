CREATE TABLE sales
(
    `sales_id`      INT NOT NULL AUTO_INCREMENT,
    `sales_date`    DATETIME    NOT NULL,
    `unit_price`    INT NOT NULL,
    `use_date`      DATETIME    NOT NULL,
    `gift_yn`       INT NOT NULL,
    `cancel_date`   DATETIME    NOT NULL,
    `currency_state`    INT NOT NULL,
    `payment_code`  INT NOT NULL,
    `payment_type_code` INT NOT NULL,
    `payment_name`  VARCHAR(30) NOT NULL,
    PRIMARY KEY (sales_id)
)default character set utf8 collate UTF8_GENERAL_CI;


//수정
CREATE TABLE sales
(
    `sales_id`      INT NOT NULL AUTO_INCREMENT,
    `sales_date`    DATETIME    NOT NULL,
    `unit_price`    INT NOT NULL,
    `use_date`      DATETIME,
    `gift_yn`       BOOLEAN NOT NULL,
    `cancel_date`   DATETIME,
    `currency_state`    VARCHAR(4) NOT NULL,
    `payment_name`  VARCHAR(30) NOT NULL,
    `id` INT              NOT NULL,
    `local_currency_voucher_id` INT              NOT NULL,
    `recipient_email` VARCHAR(50),
    PRIMARY KEY (sales_id)
)default character set utf8 collate UTF8_GENERAL_CI;