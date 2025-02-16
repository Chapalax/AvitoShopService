CREATE SEQUENCE IF NOT EXISTS transfer_id_seq;

CREATE TABLE IF NOT EXISTS transfers
(
    id        BIGINT DEFAULT nextval('transfer_id_seq') PRIMARY KEY,
    sender    BIGINT REFERENCES users (id) ON DELETE RESTRICT NOT NULL,
    recipient BIGINT REFERENCES users (id) ON DELETE RESTRICT NOT NULL,
    amount    INT                                             NOT NULL CHECK ( amount >= 0 )
);

ALTER SEQUENCE transfer_id_seq OWNED BY transfers.id;