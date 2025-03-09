-- Create the customers table first
CREATE TABLE customers (
    customer_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    PRIMARY KEY (customer_id)
);

-- Insert data into the customers table
INSERT INTO customers VALUES 
(1, 'Innovative Solutions Ltd.', 'contact@innovativesolutions.com', '123-456-7890'),
(2, 'Strategic Marketing Group', 'info@strategicmarketing.com', '234-567-8901'),
(3, 'Creative Design Hub', 'support@creativedesign.com', '345-678-9012'),
(4, 'Beverage Blends', NULL, NULL);


CREATE TABLE tasks (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    task_name VARCHAR(255) NOT NULL,
    hourly_rate DECIMAL(10, 2) NOT NULL
);


CREATE TABLE bills (
    bill_id INT NOT NULL AUTO_INCREMENT,
    customer_id INT DEFAULT NULL,
    bill_date DATE DEFAULT (CURDATE()),
    total DECIMAL(10, 2) DEFAULT NULL,
    PRIMARY KEY (bill_id),
    KEY customer_id (customer_id),
    CONSTRAINT bills_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE bill_services (
    bill_service_id INT PRIMARY KEY AUTO_INCREMENT,
    bill_id INT,
    service_id INT,
    quantity INT,
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

CREATE TABLE services (
    service_id INT PRIMARY KEY,
    service_name VARCHAR(255),
    price DECIMAL(10, 2)
);

INSERT INTO `services` (`service_id`, `service_name`, `price`) VALUES
(1, 'Software Consulting', 150.00),
(2, 'Database Administration', 120.00),
(3, 'Network Security Audit', 180.00),
(4, 'Mobile App Development', 200.00),
(5, 'Cloud Infrastructure Setup', 250.00),
(6, 'Technical Project Management', 300.00),
(7, 'System Maintenance', 100.00);
