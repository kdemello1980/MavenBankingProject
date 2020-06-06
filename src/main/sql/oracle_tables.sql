DROP TABLE accounts CASCADE CONSTRAINTS;
DROP TABLE account_status CASCADE CONSTRAINTS;
DROP TABLE account_types CASCADE CONSTRAINTS;
DROP TABLE permissions CASCADE CONSTRAINTS;
DROP TABLE role_permissions CASCADE CONSTRAINTS;
DROP TABLE roles CASCADE CONSTRAINTS;
DROP TABLE user_accounts CASCADE CONSTRAINTS;
DROP TABLE users CASCADE CONSTRAINTS;

CREATE TABLE accounts (
    account_id NUMBER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
    balance NUMBER(38,2) NOT NULL,
    status NUMBER,
    type NUMBER,
    PRIMARY KEY(account_id)
);
-- account_status --
CREATE TABLE account_status(
    status_id NUMBER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
    status VARCHAR2(64) UNIQUE,
    PRIMARY KEY(status_id)
);

-- account_types --
CREATE TABLE account_types(
    type_id NUMBER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
    type VARCHAR2(64) UNIQUE,
    interest_rate NUMBER(38,2),
    monthly_fee NUMBER(38,2),
	permission_id NUMBER,
    PRIMARY KEY(type_id)
);

-- users --
CREATE TABLE users(
    user_id NUMBER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
    username VARCHAR2(64) NOT NULL UNIQUE,
    user_pwd VARCHAR(64) NOT NULL,
    email VARCHAR2(64) NOT NULL UNIQUE,
    firstname VARCHAR2(64) NOT NULL,
    lastname VARCHAR2(64)  NOT NULL,
    role NUMBER,
    PRIMARY KEY(user_id)
);

-- roles --
CREATE TABLE roles(
    role_id NUMBER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
	name VARCHAR2(64) UNIQUE,
    PRIMARY KEY(role_id)
);

-- permissions --
-- I think I can put a UNIQUE constrint on the --
-- name column and remove it from the primary --
-- key here. --
CREATE TABLE permissions (
    permission_id NUMBER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1),
    permission_name VARCHAR2(64) NOT NULL UNIQUE,
    PRIMARY KEY(permission_id)
);
-- role_permissions --
CREATE TABLE role_permissions (
    role_id NUMBER,
    permission_id NUMBER,
    PRIMARY KEY(role_id, permission_id)
);
-- user_accounts
CREATE TABLE user_accounts(
    account_id NUMBER,
    user_id NUMBER,
    PRIMARY KEY(account_id, user_id)
);
-- constraints --
ALTER TABLE accounts
    ADD CONSTRAINT fk_accounts_type FOREIGN KEY (type) REFERENCES account_types(type_id);
ALTER TABLE accounts
    ADD CONSTRAINT fk_accounts_status FOREIGN KEY (status) REFERENCES account_status(status_id);
    
ALTER TABLE account_types
	ADD CONSTRAINT fk_account_types_permissions FOREIGN KEY (permission_id) REFERENCES permissions(permission_id);
                  
ALTER TABLE users
ADD CONSTRAINT fk_users_role FOREIGN KEY (role) REFERENCES roles(role_id);

ALTER TABLE user_accounts
    ADD CONSTRAINT fk_user_accounts_account FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE;
ALTER TABLE user_accounts
    ADD CONSTRAINT fk_user_accounts_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_role_permission_role FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE;
ALTER TABLE role_permissions
    ADD CONSTRAINT fk_role_permission_permission FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE;
--
