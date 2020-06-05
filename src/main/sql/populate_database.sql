
-- roles --
INSERT INTO roles (name) VALUES
('Standard'),('Premium'),('Employee'),('Admin');

-- account_types --
INSERT INTO account_types (type, interest_rate, monthly_fee) VALUES
('Basic Checking', '0', '5.00'),
('Premium Checking','0', '0.00'),
('Savings', '.03', '0.00');

-- account_status --
INSERT INTO account_status (status) VALUES
('Pending'), ('Open'),('Closed'),('Denied');

-- account --
INSERT INTO accounts (balance, status, type)
VALUES ('5.00', '1', '1'),('1000.00','2','2');


-- permissinons --


