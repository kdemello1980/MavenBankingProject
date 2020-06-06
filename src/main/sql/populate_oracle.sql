
-- roles --
DELETE  FROM roles;
ALTER TABLE roles MODIFY role_id GENERATED AS IDENTITY (START WITH 1);
INSERT INTO roles (name) VALUES ('Standard'); -- 1
INSERT INTO roles (name) VALUES ('Premium'); -- 2
INSERT INTO roles (name) VALUES ('Employee'); -- 3
INSERT INTO roles (name) VALUES ('Admin'); -- 4


-- account_status --
DELETE FROM account_status;
ALTER TABLE account_status MODIFY status_id GENERATED AS IDENTITY (START WITH 1);
INSERT INTO account_status (status) VALUES ('Pending');
INSERT INTO account_status (status) VALUES ('Open');
INSERT INTO account_status (status) VALUES ('Closed');
INSERT INTO account_status (status) VALUES ('Denied');


-- permissinons --
DELETE FROM permissions;
ALTER TABLE permissions MODIFY permission_id  GENERATED AS IDENTITY (START WITH 1);
INSERT INTO permissions (permission_name) VALUES ('sp_can_transfer'); -- standard, premium 1
INSERT INTO permissions (permission_name) VALUES ('sp_can_deposit'); -- standard, premium 2
INSERT INTO permissions (permission_name) VALUES ('sp_can_withdraw'); -- standard, premium 3
INSERT INTO permissions (permission_name) VALUES ('ea_can_view_all_customer_info'); -- employee, admin 4
INSERT INTO permissions (permission_name) VALUES ('e_can_modify_all_customer_info'); -- admin 5
INSERT INTO permissions (permission_name) VALUES ('p_can_open_joint_account'); -- premium 6 
INSERT INTO permissions (permission_name) VALUES ('p_can_add_user_to_account'); -- premium 7
INSERT INTO permissions (permission_name) VALUES ('sp_can_view_customer_info'); -- standard, premium 8
INSERT INTO permissions (permission_name) VALUES ('sp_can_modify_customer_info'); -- standard, premium 9
INSERT INTO permissions (permission_name) VALUES ('s_can_upgrade_status_to_premium'); -- standard 10
INSERT INTO permissions (permission_name) VALUES ('ea_can_add_new_user'); -- employee, admin 11
INSERT INTO permissions (permission_name) VALUES ('a_can_add_new_employee'); -- admin 12
INSERT INTO permissions (permission_name) VALUES ('sp_account_types_standard'); -- standard, premium 13
INSERT INTO permissions (permission_name) VALUES ('sp_account_types_premium'); -- premium, 14

-- account_types --
DELETE FROM account_types;
ALTER TABLE account_types MODIFY type_id GENERATED AS IDENTITY (START WITH 1);
INSERT INTO account_types (type, interest_rate, monthly_fee, permission_id) VALUES ('Basic Checking', '0', '5.00', 13);
INSERT INTO account_types (type, interest_rate, monthly_fee, permission_id) VALUES ('Premium Checking','0', '0.00', 14);
INSERT INTO account_types (type, interest_rate, monthly_fee, permission_id) VALUES ('Basic Savings', '.03', '0.00', 13);
INSERT INTO account_types (type, interest_rate, monthly_fee, permission_id) VALUES ('Premium Savings', '.05', '0.00', 14);

-- account --
DELETE FROM accounts;
ALTER TABLE accounts MODIFY account_id GENERATED AS IDENTITY (START WITH 1);
INSERT INTO accounts (balance, status, type) VALUES ('5.00', '1', '1');
INSERT INTO accounts (balance, status, type) VALUES ('1000.00','2','2');
INSERT INTO accounts (balance, status, type) VALUES ('6000.00', '1', '3');


-- role_permissions --
-- 1 - standard
-- 2 - premium
-- 3 - employee
-- 4 - admin
DELETE FROM role_permissions;
-- standard user permissions 
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 3);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 8);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 9);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 10);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 13);
-- premium user permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 3);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 6);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 7);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 8);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 9);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2,13);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 14);
-- employee permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 4);
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 11);
-- admin permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 4);
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 5);
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 12);



SELECT * FROM roles;
SELECT * FROM role_permissions;
SELECT * FROM permissions;
