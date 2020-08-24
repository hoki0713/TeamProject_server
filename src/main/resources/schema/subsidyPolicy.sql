CREATE TABLE subsidyPolicy
(
    `subsidy_policy_id` INT NOT NULL AUTO_INCREMENT,
    `policy_name` VARCHAR(15) INT NULL,
    `condi_age` INT NULL,
    `condi_resd_duration`  INT NULL,
    `condi_children_age` INT NULL,
    `policy_desc` VARCHAR(100),
    `policy_url` VARCHAR (50),
    PRIMARY KEY (subsidy_policy_id)
)DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;

