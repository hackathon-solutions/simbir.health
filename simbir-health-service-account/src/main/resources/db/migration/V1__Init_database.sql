CREATE TABLE IF NOT EXISTS accounts (
    account_id BIGSERIAL NOT NULL,
    first_name VARCHAR(500),
    last_name VARCHAR(500),
    username VARCHAR(500),
    password VARCHAR(500),
    account_role SMALLINT
);