CREATE TABLE subsidyPolicy
(
    `subsidy_policy_id` INT NOT NULL AUTO_INCREMENT,
    `condition_age` INT NULL,
    `condition_income`  INT NULL,
    `condition_dependents` INT NULL,
    PRIMARY KEY (subsidy_policy_id)
)default character set utf8 collate UTF8_GENERAL_CI;