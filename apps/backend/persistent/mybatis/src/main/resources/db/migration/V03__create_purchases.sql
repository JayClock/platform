CREATE TABLE `purchases`
(
    `id`         BIGINT PRIMARY KEY,
    `purchaser_id` BIGINT REFERENCES PURCHASERS (user_id),
    `created_at` TIMESTAMP
);