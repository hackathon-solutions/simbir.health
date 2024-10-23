CREATE TABLE IF NOT EXISTS accounts (
    account_id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(500) NOT NULL,
    last_name VARCHAR(500) NOT NULL,
    account_roles VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS account_credentials (
    account_id BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(500) UNIQUE NOT NULL,
    password VARCHAR(500) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (account_id) ON DELETE CASCADE
);