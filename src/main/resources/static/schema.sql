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
)default character set utf8 collate UTF8_GENERAL_CI;;
CREATE TABLE recommend
(
    `recommend_type` INT NOT NULL,
    `recommend_tag`  VARCHAR(50) NULL,
    PRIMARY KEY (recommend_type)
)default character set utf8 collate UTF8_GENERAL_CI;;
CREATE TABLE subsidyPolicy
(
    `subsidy_policy_id` INT NOT NULL AUTO_INCREMENT,
    `condition_age` INT NULL,
    `condition_income`  INT NULL,
    `condition_dependents` INT NULL,
        PRIMARY KEY (subsidy_policy_id)
)default character set utf8 collate UTF8_GENERAL_CI;;
