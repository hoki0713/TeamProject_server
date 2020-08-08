CREATE TABLE recommend
(recommend_id INT NOT NULL AUTO_INCREMENT,
    `recommend_type` INT NOT NULL,
    `recommend_tag`  VARCHAR(50) NULL,
    PRIMARY KEY (recommend_id)
)default character set utf8 collate UTF8_GENERAL_CI;