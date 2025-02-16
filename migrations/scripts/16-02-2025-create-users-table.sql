CREATE SEQUENCE IF NOT EXISTS user_id_seq;

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT           DEFAULT nextval('user_id_seq') PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    balance  INT     NOT NULL DEFAULT 1000 CHECK ( balance >= 0 )
);

ALTER SEQUENCE user_id_seq OWNED BY users.id;