/* 회원 테이블 */
CREATE TABLE user
(
    `user_id`        INT             NOT NULL    AUTO_INCREMENT,
    `id`             VARCHAR(30)     NULL,
    `password`       VARCHAR(80)     NULL,
    `name`           VARCHAR(10)     NULL,
    `birth_date`     TIMESTAMP       NULL,
    `gender`         VARCHAR(2)      NULL,
    `email`          VARCHAR(100)    NULL,
    `join_date`      TIMESTAMP       NULL,
    `withdraw_date`  TIMESTAMP       NULL,
    `admin_key`      INT             NULL,
    `card_number`    VARCHAR(50)     NULL,
    PRIMARY KEY (user_id)
)default character set utf8 collate UTF8_GENERAL_CI;

/* 주소 테이블 */
CREATE TABLE address
(
    `address_id`     INT             NOT NULL    AUTO_INCREMENT,
    `default_addr`   VARCHAR(100)    NULL,
    `optional_addr`  VARCHAR(100)    NULL,
    PRIMARY KEY (address_id)
)default character set utf8 collate UTF8_GENERAL_CI;

/* 지역화폐 테이블 */
CREATE TABLE local_currency
(
    `local_currency_id`  INT            NOT NULL    AUTO_INCREMENT,
    `use_local`          VARCHAR(10)    NULL,
    PRIMARY KEY (local_currency_id)
)default character set utf8 collate UTF8_GENERAL_CI;