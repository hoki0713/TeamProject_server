CREATE TABLE subsidyPolicy
(
    `subsidy_policy_id` INT NOT NULL AUTO_INCREMENT,
    `policy_name` varchar(15) not null,
    `condi_age` INT NULL,
    `condi_resd_duration`  INT NULL,
    `condi_children_age` INT NULL,
    `policy_desc` varchar(100),
    `policy_url` varchar (50),
    PRIMARY KEY (subsidy_policy_id)
)default character set utf8 collate UTF8_GENERAL_CI;

