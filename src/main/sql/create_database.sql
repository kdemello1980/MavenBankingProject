-- banking database --
DROP DATABASE IF EXISTS banking;
CREATE DATABASE banking;
\c banking

-- acounts table --
CREATE TABLE accounts (
    id SERIAL NOT NULL,
    balance NUMERIC(1000,2) NOT NULL DEFAULT '0.00',
    status SERIAL,
    type SERIAL,
    PRIMARY KEY(id)
);

-- account_status --
CREATE TABLE account_status(
    id  SERIAL UNIQUE,
    status VARCHAR(64) UNIQUE,
    PRIMARY KEY(id, status)
);

-- account_types --
CREATE TABLE account_types(
    id SERIAL UNIQUE,
    type VARCHAR(64) UNIQUE,
    interest_rate DECIMAL,
    monthly_fee MONEY,
    PRIMARY KEY(id, type)
);

-- users --
CREATE TABLE users(
    id SERIAL,
    username VARCHAR(64) NOT NULL UNIQUE,
    email VARCHAR(64) NOT NULL UNIQUE,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64)  NOT NULL,
    role SERIAL,
    PRIMARY KEY(id)
);

-- roles --
CREATE TABLE roles(
    id SERIAL UNIQUE,
	name VARCHAR(64) UNIQUE,
    PRIMARY KEY(id, name)
);

-- permissions --
-- I think I can put a UNIQUE constrint on the --
-- name column and remove it from the primary --
-- key here. --
CREATE TABLE permissions (
    id SERIAL UNIQUE,
    name VARCHAR(64),
    PRIMARY KEY(id, name)
);
-- role_permissions --
CREATE TABLE role_permissions (
    role_id SERIAL,
    permission_id SERIAL
);
CREATE TABLE user_accounts(
    account_id SERIAL,
    user_id SERIAL,
    PRIMARY KEY(account_id, user_id)
);
-- constraints --
ALTER TABLE accounts
ADD CONSTRAINT fk_accounts_type FOREIGN KEY (type) REFERENCES account_types(id),
ADD CONSTRAINT fk_accounts_status FOREIGN KEY (status) REFERENCES account_status(id);
ALTER TABLE users
ADD CONSTRAINT fk_users_role FOREIGN KEY (role) REFERENCES roles(id);
ALTER TABLE user_accounts
ADD CONSTRAINT fk_user_accounts_account FOREIGN KEY (account_id) REFERENCES accounts(id),
ADD CONSTRAINT fk_user_accounts_user FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE role_permissions
ADD CONSTRAINT fk_role_permission_role FOREIGN KEY (role_id) REFERENCES roles(id),
ADD CONSTRAINT fk_role_permission_permission FOREIGN KEY (permission_id) REFERENCES permissions(id);
--

