DROP TABLE kmdm_accounts CASCADE;
DROP TABLE kmdm_account_status CASCADE;
DROP TABLE kmdm_account_types CASCADE;
DROP TABLE kmdm_permissions CASCADE;
DROP TABLE kmdm_role_permissions CASCADE;
DROP TABLE kmdm_roles CASCADE;
DROP TABLE kmdm_user_accounts CASCADE;
DROP TABLE kmdm_users CASCADE;

CREATE TABLE kmdm_accounts (
    account_id SERIAL,
    balance NUMERIC(38,2) NOT NULL DEFAULT 0.00,
    status INTEGER,
    type_id INTEGER,
    PRIMARY KEY(account_id)
);
-- account_status --
CREATE TABLE kmdm_account_status(
    status_id SERIAL,
    status VARCHAR(64) NOT NULL UNIQUE,
    PRIMARY KEY(status_id)
);

-- account_types --
CREATE TABLE kmdm_account_types(
    type_id SERIAL,
    type_name VARCHAR(64) NOT NULL UNIQUE,
    interest_rate NUMERIC(38,2),
    monthly_fee NUMERIC(38,2),
    compound_months INTEGER,
	permission_id INTEGER,
    PRIMARY KEY(type_id)
);

-- users --
CREATE TABLE kmdm_users(
    user_id SERIAL,
    username VARCHAR(64) NOT NULL UNIQUE,
    user_pwd VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64)  NOT NULL,
    role INTEGER,
    PRIMARY KEY(user_id)
);

-- roles --
CREATE TABLE kmdm_roles(
    role_id SERIAL,
	name VARCHAR(64) UNIQUE,
    PRIMARY KEY(role_id)
);

-- permissions --
-- I think I can put a UNIQUE constrint on the --
-- name column and remove it from the primary --
-- key here. --
CREATE TABLE kmdm_permissions (
    permission_id SERIAL,
    permission_name VARCHAR(64) NOT NULL UNIQUE,
    PRIMARY KEY(permission_id)
);
-- role_permissions --
CREATE TABLE kmdm_role_permissions (
    role_id INTEGER,
    permission_id INTEGER,
    PRIMARY KEY(role_id, permission_id)
);
-- user_accounts
CREATE TABLE kmdm_user_accounts(
    account_id INTEGER,
    user_id INTEGER,
    PRIMARY KEY(account_id, user_id)
);
-- constraints --
ALTER TABLE kmdm_accounts
    ADD CONSTRAINT fk_accounts_type FOREIGN KEY (type_id) REFERENCES kmdm_account_types(type_id);
ALTER TABLE kmdm_accounts
    ADD CONSTRAINT fk_accounts_status FOREIGN KEY (status) REFERENCES kmdm_account_status(status_id);
    
ALTER TABLE kmdm_account_types
	ADD CONSTRAINT fk_account_types_permissions FOREIGN KEY (permission_id) REFERENCES kmdm_permissions(permission_id);
                  
ALTER TABLE kmdm_users
ADD CONSTRAINT fk_users_role FOREIGN KEY (role) REFERENCES kmdm_roles(role_id);

ALTER TABLE kmdm_user_accounts
    ADD CONSTRAINT fk_user_accounts_account FOREIGN KEY (account_id) REFERENCES kmdm_accounts(account_id);
ALTER TABLE kmdm_user_accounts
    ADD CONSTRAINT fk_user_accounts_user FOREIGN KEY (user_id) REFERENCES kmdm_users(user_id);

ALTER TABLE kmdm_role_permissions
    ADD CONSTRAINT fk_role_permission_role FOREIGN KEY (role_id) REFERENCES kmdm_roles(role_id) ON DELETE CASCADE;
ALTER TABLE kmdm_role_permissions
    ADD CONSTRAINT fk_role_permission_permission FOREIGN KEY (permission_id) REFERENCES kmdm_permissions(permission_id) ON DELETE CASCADE;
--
