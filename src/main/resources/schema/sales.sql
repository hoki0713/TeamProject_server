CREATE TABLE sales
(
    `sales_id`      INT NOT NULL AUTO_INCREMENT,
    `sales_date`    DATE    NOT NULL,
    `unit_price`    INT NOT NULL,
    `use_date`      DATE,
    `gift_yn`       BOOLEAN NOT NULL,
    `cancel_date`   DATE,
    `currency_state`    VARCHAR(4) NOT NULL,
    `payment_name`  VARCHAR(30) NOT NULL,
    `user_id` INT              NOT NULL,
    `local_currency_voucher_id` INT NOT NULL,
    `recipient_email` VARCHAR(50),
    PRIMARY KEY (sales_id)
)DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;