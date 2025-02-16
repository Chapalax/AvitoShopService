CREATE SEQUENCE IF NOT EXISTS merch_id_seq;

CREATE TABLE IF NOT EXISTS merch
(
    id    BIGINT           DEFAULT nextval('merch_id_seq') PRIMARY KEY,
    item  VARCHAR NOT NULL UNIQUE,
    price INT     NOT NULL DEFAULT 100 CHECK ( price >= 0 )
);

ALTER SEQUENCE merch_id_seq OWNED BY merch.id;

INSERT INTO merch (item, price)
VALUES ('t-shirt', 80),
       ('cup', 20),
       ('book', 50),
       ('pen', 10),
       ('powerbank', 200),
       ('hoody', 300),
       ('umbrella', 200),
       ('socks', 10),
       ('wallet', 50),
       ('pink-hoody', 500);