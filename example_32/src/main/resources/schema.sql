CREATE TABLE IF NOT EXISTS `contact.msg` (
    `contact_id` int AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL,
    `mobile_num` varchar(10) NOT NULL,
    `email` varchar(100) NOT NULL,
    `subject` varchar(100) NOT NULL,
    `message` varchar(500) NOT NULL,
    `status` varchar(10) NOT NULL,
    `create_at` TIMESTAMP NOT NULL,
    `create_by` varchar(50) NOT NULL,
    `update_at` TIMESTAMP DEFAULT NULL,
    `update_by` varchar(50) DEFAULT NULL
);
