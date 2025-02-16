CREATE TABLE IF NOT EXISTS inventory
(
    user_id  BIGINT REFERENCES users (id) ON DELETE CASCADE  NOT NULL,
    merch_id BIGINT REFERENCES merch (id) ON DELETE RESTRICT NOT NULL,
    PRIMARY KEY (user_id, merch_id),
    amount   INT                                             NOT NULL CHECK ( amount >= 0 )
);
