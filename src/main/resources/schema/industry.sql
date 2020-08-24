CREATE TABLE industry (
                          industry_id	INT	NOT NULL AUTO_INCREMENT,
                          industry_code	INT	NOT NULL,
                          main_code	VARCHAR(20)	NOT NULL,
                          store_type VARCHAR(20)	NOT NULL,
                          industry_image_url	VARCHAR(200)	NOT NULL,
                          PRIMARY KEY (industry_id)
)DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;