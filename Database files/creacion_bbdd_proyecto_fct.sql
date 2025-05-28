DROP DATABASE IF EXISTS proyecto_final_fct;
CREATE DATABASE proyecto_final_fct
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci; -- This line is to make that the database is Case Insensitive
USE proyecto_final_fct;

-- Creation of tables
CREATE TABLE Landlord(
	id INT PRIMARY KEY AUTO_INCREMENT,
    DNI VARCHAR(10) UNIQUE KEY NOT NULL,
    name VARCHAR(50) NOT NULL);
CREATE INDEX ix_id_landlord ON Landlord(id);

-- I've created these two tables because the email and the phone number are multi-valued fields
CREATE TABLE Landlord_Email(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_landlord INT NOT NULL,
	email VARCHAR(50),
    CONSTRAINT fk_id_landlord_landlord_email_landlord FOREIGN KEY(id_landlord) REFERENCES Landlord(id));
CREATE INDEX ix_id_id_landlord_landlord_email ON Landlord_Email(id, id_landlord);
 
CREATE TABLE Landlord_Phone_Number(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_landlord INT NOT NULL,
	phone_number VARCHAR(50),
    CONSTRAINT fk_id_landlord_landlord_phone_number_landlord FOREIGN KEY(id_landlord) REFERENCES Landlord(id));
CREATE INDEX ix_id_landlord_phone_number ON Landlord_Phone_Number(id, id_landlord);   
    
-- I've created this table in case that someone wants to add a house type or alter the name of one of those types
CREATE TABLE House_Type(
	id INT PRIMARY KEY,
    name VARCHAR(20),
    description VARCHAR(100));
CREATE INDEX ix_id_house_type ON House_Type(id);
    
CREATE TABLE Tenement(
	id VARCHAR(10) NOT NULL,
    id_landlord INT NOT NULL,
    rent_price DECIMAL(6,2) DEFAULT 0,
    surface DECIMAL(5,2),
    description VARCHAR(100),
    type INT,
    accepts_pets BOOLEAN,
    address VARCHAR(100),
    PRIMARY KEY (id, id_landlord),
    CONSTRAINT fk_id_landlord_landlord FOREIGN KEY(id_landlord) REFERENCES Landlord(id),
    CONSTRAINT fk_type_house_type FOREIGN KEY(type) REFERENCES House_Type(id));
CREATE INDEX ix_id_id_landlord_tenement ON Tenement(id, id_landlord);
    
CREATE TABLE Tenant(
	id INT PRIMARY KEY AUTO_INCREMENT,
    DNI VARCHAR(10) UNIQUE KEY,
    name VARCHAR(50),
    has_pet BOOLEAN,
    email VARCHAR(50));
CREATE INDEX ix_id_tenant ON Tenant(id);

-- I've created these two tables because it's the same case as the one before, the fields are multi-valued
CREATE TABLE Tenant_Email(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_tenant INT NOT NULL,
	email VARCHAR(50),
    CONSTRAINT fk_id_tenant_tenant_email_tenant FOREIGN KEY(id_tenant) REFERENCES Tenant(id));
CREATE INDEX ix_id_id_tenant_tenant_email ON Tenant_Email(id, id_tenant);

CREATE TABLE Tenant_Phone_Number(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_tenant INT NOT NULL,
	phone_number VARCHAR(20),
    CONSTRAINT fk_id_tenant_tenant_phone_number_tenant FOREIGN KEY(id_tenant) REFERENCES Tenant(id));
CREATE INDEX ix_id_id_tenant_tenant_phone_number ON Tenant_Phone_Number(id, id_tenant);

-- I've considered to make the primary keys the combination of both id's and the starting date so the contract can be easily identified
CREATE TABLE Contract(
    id_tenement VARCHAR(10),
    id_tenant INT,
    start_date DATE,
    finish_date DATE,
    price DECIMAL(6,2),
    contract_status ENUM('PENDING', 'ACTIVE', 'EXPIRED'), -- This is an enum because the contracts can only be on 3 different status
    PRIMARY KEY (id_tenement, id_tenant, start_date));
CREATE INDEX ix_id_tenement_id_tenant_start_date_contract ON Contract(id_tenement, id_tenant, start_date);
-- ----------------------------------------------------------------------------------------
-- This is the creation of the log tables, which are almost the same as the normal tables, with the exception that they hold a bit more of information
CREATE TABLE Landlord_log(
	id_log INT PRIMARY KEY AUTO_INCREMENT,
    id INT,
    DNI VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL,
    change_type ENUM ('INSERT', 'DELETE', 'MODIFY'),
    change_date_time DATETIME DEFAULT NOW());

-- I've created these two tables because the email and the phone number are multi-valued fields
CREATE TABLE Landlord_Email_log(
	id_log INT PRIMARY KEY AUTO_INCREMENT,
	id INT,
    id_landlord INT NOT NULL,
	email VARCHAR(50),
    change_type ENUM ('INSERT', 'DELETE', 'MODIFY'),
    change_date_time DATETIME DEFAULT NOW());
    
CREATE TABLE Landlord_Phone_Number_log(
	id_log INT PRIMARY KEY AUTO_INCREMENT,
	id INT,
    id_landlord INT NOT NULL,
	phone_number VARCHAR(50),
    change_type ENUM ('INSERT', 'DELETE', 'MODIFY'),
    change_date_time DATETIME DEFAULT NOW());
    
-- I've created this table in case that someone wants to add a house type or alter the name of one of those types
CREATE TABLE House_Type_log(
	id_log INT PRIMARY KEY AUTO_INCREMENT,
	id INT,
    name VARCHAR(20),
    change_type ENUM ('INSERT', 'DELETE', 'MODIFY'),
    change_date_time DATETIME DEFAULT NOW(),
    description VARCHAR(100));
    
CREATE TABLE Tenement_log(
	id_log INT PRIMARY KEY AUTO_INCREMENT,
	id VARCHAR(10) NOT NULL,
    id_landlord INT NOT NULL,
    rent_price DECIMAL(6,2) DEFAULT 0,
    surface DECIMAL(5,2),
    description VARCHAR(100),
    type INT,
    accepts_pets BOOLEAN,
    address VARCHAR(100),
    change_type ENUM ('INSERT', 'DELETE', 'MODIFY'),
    change_date_time DATETIME DEFAULT NOW());
    
CREATE TABLE Tenant_log(
	id_log INT PRIMARY KEY AUTO_INCREMENT,
	id INT ,
    DNI VARCHAR(10) UNIQUE KEY,
    phone_number VARCHAR(20),
    name VARCHAR(50),
    has_pet BOOLEAN,
    email VARCHAR(50),
    change_type ENUM ('INSERT', 'DELETE', 'MODIFY'),
    change_date_time DATETIME DEFAULT NOW());

-- I've created these two tables because it's the same case as the one before, the fields are multi-valued
CREATE TABLE Tenant_Email_log(
	id_log INT PRIMARY KEY AUTO_INCREMENT,
	id INT,
    id_tenant INT NOT NULL,
	email VARCHAR(50),
    change_type ENUM ('INSERT', 'DELETE', 'MODIFY'),
    change_date_time DATETIME DEFAULT NOW());
    
CREATE TABLE Tenant_Phone_Number_log(
	id_log INT PRIMARY KEY AUTO_INCREMENT,
	id INT,
    id_tenant INT NOT NULL,
	phone_number VARCHAR(50),
    change_type ENUM ('INSERT', 'DELETE', 'MODIFY'),
    change_date_time DATETIME DEFAULT NOW());

-- I've considered to make the primary keys the combination of both id's and the starting date so the contract can be easily identified
CREATE TABLE Contract_log(
	id_log INT PRIMARY KEY AUTO_INCREMENT,
    id_tenement VARCHAR(10),
    id_tenant INT,
    start_date DATE,
    finish_date DATE,
    price DECIMAL(6,2),
    contract_status ENUM('PENDING', 'ACTIVE', 'EXPIRED'), -- This is an enum because the contracts can only be on 3 different status
    change_type ENUM ('INSERT', 'DELETE', 'MODIFY'),
    change_date_time DATETIME DEFAULT NOW());