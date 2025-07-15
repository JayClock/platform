CREATE TABLE `purchasers`
(
    `user_id` BIGINT PRIMARY KEY REFERENCES USERS (id), -- 一对一关系
    `amount`  DECIMAL(10, 2)
);