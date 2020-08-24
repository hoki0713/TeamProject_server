CREATE TABLE post
(
    `post_id`       INT              NOT NULL    AUTO_INCREMENT,
    `board_id`      INT              NOT NULL,
    `reg_date`      DATE             NOT NULL,
    `category`      VARCHAR(10),
    `post_title`    VARCHAR(30),
    `contents`      VARCHAR(1000)    NOT NULL,
    `read_count`    INT,
    `modi_date`     DATE,
    `notice_yn`     BOOLEAN          NOT NULL,
    `delete_yn`     BOOLEAN          NOT NULL,
    `comment`       VARCHAR(500),
    `user_id`       INT              NOT NULL,
    `rating_id`     INT,
    PRIMARY KEY (post_id)
)DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;