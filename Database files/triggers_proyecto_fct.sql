USE proyecto_final_fct;
DELIMITER //
-- Contract table triggers
CREATE TRIGGER  tr_contract_after_insert AFTER INSERT ON Contract
FOR EACH ROW
BEGIN
	INSERT INTO Contract_log(id_tenement, id_tenant, start_date, finish_date, price, contract_status, change_type) 
    VALUES (NEW.id_tenement, NEW.id_tenant, NEW.start_date, NEW.finish_date, NEW.price, NEW.contract_status, 'INSERT');
END//
CREATE TRIGGER tr_contract_after_update AFTER UPDATE ON Contract
FOR EACH ROW
BEGIN
	INSERT INTO Contract_log(id_tenement, id_tenant, start_date, finish_date, price, contract_status, change_type) 
    VALUES (NEW.id_tenement, NEW.id_tenant, NEW.start_date, NEW.finish_date, NEW.price, NEW.contract_status, 'UPDATE');
END//
CREATE TRIGGER tr_contract_before_delete BEFORE DELETE ON Contract
FOR EACH ROW
BEGIN
	INSERT INTO Contract_log(id_tenement, id_tenant, start_date, finish_date, price, contract_status, change_type) 
    VALUES (OLD.id_tenement, OLD.id_tenant, OLD.start_date, OLD.finish_date, OLD.price, OLD.contract_status, 'DELETE');
END//

-- House type table triggers
CREATE TRIGGER tr_house_type_after_insert AFTER INSERT ON House_Type
FOR EACH ROW
BEGIN
	INSERT INTO House_Type_log(id, name, description, change_type) 
    VALUES (NEW.id, NEW.name, NEW.description, 'INSERT');
END//
CREATE TRIGGER tr_house_type_after_update AFTER UPDATE ON House_Type
FOR EACH ROW
BEGIN
	INSERT INTO House_Type_log(id, name, description, change_type) 
    VALUES (NEW.id, NEW.name, NEW.description, 'UPDATE');
END//
CREATE TRIGGER tr_house_type_before_delete BEFORE DELETE ON House_Type
FOR EACH ROW
BEGIN
	INSERT INTO House_Type_log(id, name, description, change_type) 
    VALUES (OLD.id, OLD.name, OLD.description, 'DELETE');
END//

-- Landlord table triggers
CREATE TRIGGER tr_landlord_after_insert AFTER INSERT ON Landlord
FOR EACH ROW
BEGIN
	INSERT INTO Landlord_log(id, DNI, name, change_type) 
    VALUES (NEW.id, NEW.DNI, NEW.name, 'INSERT');
END//
CREATE TRIGGER tr_landlord_after_update AFTER UPDATE ON Landlord
FOR EACH ROW
BEGIN
	INSERT INTO Landlord_log(id, DNI, name, change_type) 
    VALUES (NEW.id, NEW.DNI, NEW.name, 'UPDATE');
END//
CREATE TRIGGER tr_landlord_before_delete BEFORE DELETE ON Landlord
FOR EACH ROW
BEGIN
	INSERT INTO Landlord_log(id, DNI, name, change_type) 
    VALUES (OLD.id, OLD.DNI, OLD.name, 'DELETE');
END//

-- Landlord email table triggers
CREATE TRIGGER tr_landlord_email_after_insert AFTER INSERT ON Landlord_Email
FOR EACH ROW
BEGIN
	INSERT INTO Landlord_Email_log(id, id_landlord, email, change_type) 
    VALUES (NEW.id, NEW.id_landlord, NEW.email, 'INSERT');
END//
CREATE TRIGGER tr_landlord_email_after_update AFTER UPDATE ON Landlord_Email
FOR EACH ROW
BEGIN
	INSERT INTO Landlord_Email_log(id, id_landlord, email, change_type) 
    VALUES (NEW.id, NEW.id_landlord, NEW.email, 'UPDATE');
END//
CREATE TRIGGER tr_landlord_email_before_delete BEFORE DELETE ON Landlord_Email
FOR EACH ROW
BEGIN
	INSERT INTO Landlord_Email_log(id, id_landlord, email, change_type) 
    VALUES (OLD.id, OLD.id_landlord, OLD.email, 'DELETE');
END//

-- Landlord phone number table triggers
CREATE TRIGGER tr_landlord_phone_number_after_insert AFTER INSERT ON Landlord_Phone_Number
FOR EACH ROW
BEGIN
	INSERT INTO Landlord_Phone_Number_log(id, id_landlord, phone_number, change_type) 
    VALUES (NEW.id, NEW.id_landlord, NEW.phone_number, 'INSERT');
END//
CREATE TRIGGER tr_landlord_phone_number_after_update AFTER UPDATE ON Landlord_Phone_Number
FOR EACH ROW
BEGIN
	INSERT INTO Landlord_Phone_Number_log(id, id_landlord, phone_number, change_type) 
    VALUES (NEW.id, NEW.id_landlord, NEW.phone_number, 'UPDATE');
END//
CREATE TRIGGER tr_landlord_phone_number_before_delete BEFORE DELETE ON Landlord_Phone_Number
FOR EACH ROW
BEGIN
	INSERT INTO Landlord_Phone_Number_log(id, id_landlord, phone_number, change_type) 
    VALUES (OLD.id, OLD.id_landlord, OLD.phone_number, 'DELETE');
END//

-- Tenant table triggers
CREATE TRIGGER tr_tenant_after_insert AFTER INSERT ON Tenant
FOR EACH ROW
BEGIN
	INSERT INTO Tenant_log(id, DNI, name, has_pet, change_type) 
    VALUES (NEW.id, NEW.DNI, NEW.name, NEW.has_pet, 'INSERT');
END//
CREATE TRIGGER tr_tenant_after_update AFTER UPDATE ON Tenant
FOR EACH ROW
BEGIN
	INSERT INTO Tenant_log(id, DNI, name, has_pet, change_type) 
    VALUES (NEW.id, NEW.DNI, NEW.name, NEW.has_pet, 'UPDATE');
END//
CREATE TRIGGER tr_tenant_before_delete BEFORE DELETE ON Tenant
FOR EACH ROW
BEGIN
	INSERT INTO Tenant_log(id, DNI, name, has_pet, change_type) 
    VALUES (OLD.id, OLD.DNI, OLD.name, OLD.has_pet, 'DELETE');
END//

-- Tenant email table triggers
CREATE TRIGGER tr_tenant_email_after_insert AFTER INSERT ON Tenant_Email
FOR EACH ROW
BEGIN
	INSERT INTO Tenant_Email_log(id, id_tenant, email, change_type) 
    VALUES (NEW.id, NEW.id_tenant, NEW.email, 'INSERT');
END//
CREATE TRIGGER tr_tenant_email_after_update AFTER UPDATE ON Tenant_Email
FOR EACH ROW
BEGIN
	INSERT INTO Tenant_Email_log(id, id_tenant, email, change_type) 
    VALUES (NEW.id, NEW.id_tenant, NEW.email, 'UPDATE');
END//
CREATE TRIGGER tr_tenant_email_before_delete BEFORE DELETE ON Tenant_Email
FOR EACH ROW
BEGIN
	INSERT INTO Tenant_Email_log(id, id_tenant, email, change_type) 
    VALUES (OLD.id, OLD.id_tenant, OLD.email, 'DELETE');
END//

-- Tenant phone number table triggers
CREATE TRIGGER tr_tenant_phone_number_after_insert AFTER INSERT ON Tenant_Phone_Number
FOR EACH ROW
BEGIN
	INSERT INTO Tenant_Phone_Number_log(id, id_tenant, phone_number, change_type) 
    VALUES (NEW.id, NEW.id_tenant, NEW.phone_number, 'INSERT');
END//
CREATE TRIGGER tr_tenant_phone_number_after_update AFTER UPDATE ON Tenant_Phone_Number
FOR EACH ROW
BEGIN
	INSERT INTO Tenant_Phone_Number_log(id, id_tenant, phone_number, change_type) 
    VALUES (NEW.id, NEW.id_tenant, NEW.phone_number, 'UPDATE');
END//
CREATE TRIGGER tr_tenant_phone_number_before_delete BEFORE DELETE ON Tenant_Phone_Number
FOR EACH ROW
BEGIN
	INSERT INTO Tenant_Phone_Number_log(id, id_tenant, phone_number, change_type) 
    VALUES (OLD.id, OLD.id_tenant, OLD.phone_number, 'DELETE');
END//

-- Tenement table triggers
CREATE TRIGGER tr_tenement_after_insert AFTER INSERT ON Tenement
FOR EACH ROW
BEGIN
	INSERT INTO Tenement_log(id, id_landlord, rent_price, surface, description, type, accepts_pets, address, change_type) 
    VALUES (NEW.id, NEW.id_landlord, NEW.rent_price, NEW.surface, NEW.description, NEW.type, NEW.accepts_pets, NEW.address, 'INSERT');
END//
CREATE TRIGGER tr_tenement_after_update AFTER UPDATE ON Tenement
FOR EACH ROW
BEGIN
	INSERT INTO Tenement_log(id, id_landlord, rent_price, surface, description, type, accepts_pets, address, change_type) 
    VALUES (NEW.id, NEW.id_landlord, NEW.rent_price, NEW.surface, NEW.description, NEW.type, NEW.accepts_pets, NEW.address, 'UPDATE');
END//
CREATE TRIGGER tr_tenement_before_delete BEFORE DELETE ON Tenement
FOR EACH ROW
BEGIN
	INSERT INTO Tenement_log(id, id_landlord, rent_price, surface, description, type, accepts_pets, address, change_type) 
    VALUES (OLD.id, OLD.id_landlord, OLD.rent_price, OLD.surface, OLD.description, OLD.type, OLD.accepts_pets, OLD.address, 'DELETE');
END//