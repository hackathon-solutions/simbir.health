CREATE TABLE IF NOT EXISTS accounts (
    account_id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(500),
    last_name VARCHAR(500),
    account_role SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS account_credentials (
    account_id BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(500),
    password VARCHAR(500)
);