CREATE TABLE subsidyPolicy
(
    `subsidy_policy_id` INT NOT NULL AUTO_INCREMENT,
    `policy_name` varchar(15) not null,
    `condi_age` INT NOT NULL,
    `condi_resd_duration`  INT NOT NULL,
    `condi_children_age` INT NOT NULL,
    `policy_desc` varchar(1000),
    `policy_url` varchar (500),
    PRIMARY KEY (subsidy_policy_id)
)default character set utf8 collate UTF8_GENERAL_CI;

