-- INSERT into Landlord
INSERT INTO Landlord (DNI, name) VALUES
('12345678A', 'María González'),
('87654321B', 'Juan Pérez'),
('11223344C', 'Laura Martínez'),
('99887766D', 'Carlos Sánchez'),
('44556677E', 'Ana López');

-- Insert into Landlord_Email
INSERT INTO Landlord_Email (id_landlord, email) VALUES
(1, 'maria.gonzalez@mail.com'),
(2, 'juan.perez@mail.com'),
(3, 'laura.martinez@mail.com'),
(4, 'carlos.sanchez@mail.com'),
(5, 'ana.lopez@mail.com');

-- Insert into Landlord_Phone_Number
INSERT INTO Landlord_Phone_Number (id_landlord, phone_number) VALUES
(1, '+34 600 123 456'),
(2, '+34 622 987 321'),
(3, '+34 633 456 789'),
(4, '+34 644 789 654'),
(5, '+34 655 321 987');

-- Insert into House_Type
INSERT INTO House_Type (id, name, description) VALUES
(1, 'Apartment', 'Housing unit located in a shared building, ideal for couples or single individuals.'),
(2, 'Penthouse', 'Apartment located on the top floor, with a private terrace and good views.'),
(3, 'House', 'Independent dwelling with multiple floors and a yard or garden.'),
(4, 'Duplex', 'Two-story dwelling connected by an internal staircase.'),
(5, 'Studio', 'Open-plan space without separate rooms, intended for one person.');


-- Insert into Tenement
INSERT INTO Tenement (id, id_landlord, rent_price, surface, description, type, accepts_pets, address) VALUES
('T001', 1, 650.00, 55.00, 'Modern apartment with balcony', 1, TRUE, '12 Mayor Street, Alicante'),
('T002', 2, 1200.00, 90.00, 'Penthouse with terrace and sea views', 2, FALSE, '34 Sea Avenue, Benidorm'),
('T003', 3, 950.00, 110.00, 'House with private garden and garage', 3, TRUE, '22 Olive Street, Elche'),
('T004', 4, 800.00, 70.00, 'Cozy duplex in a quiet area', 4, FALSE, '18 Moon Street, Torrevieja'),
('T005', 5, 500.00, 40.00, 'Downtown studio, ideal for students', 5, TRUE, '8 Sun Street, Orihuela');
INSERT INTO Tenement (id, id_landlord, rent_price, surface, description, type, accepts_pets, address) VALUES
('T006', 2, 700.00, 60.00, 'Retro apartment with hippie vibes', 1, TRUE, '13 Del Mar Street, Torrevieja');

-- Insert into Tenant
INSERT INTO Tenant (DNI, name, has_pet, email) VALUES
('32165498A', 'Lucía Navarro', TRUE, 'lucia.navarro@mail.com'),
('78945612B', 'David Ruiz', FALSE, 'david.ruiz@mail.com'),
('14785236C', 'Elena Torres', TRUE, 'elena.torres@mail.com'),
('96325874D', 'Pablo Ortega', FALSE, 'pablo.ortega@mail.com'),
('25874136E', 'Marta Jiménez', TRUE, 'marta.jimenez@mail.com');

-- Insert into Tenant_Email
INSERT INTO Tenant_Email (id_tenant, email) VALUES
(1, 'lucia.navarro.alt@mail.com'),
(2, 'david.ruiz.personal@mail.com'),
(3, 'elena.torres.work@mail.com'),
(4, 'pablo.ortega.extra@mail.com'),
(5, 'marta.jimenez.contacto@mail.com');


-- Insert into Tenant_Phone_Number
INSERT INTO Tenant_Phone_Number (id_tenant, phone_number) VALUES
(1, '+34 611 234 567'),
(2, '+34 622 345 678'),
(3, '+34 633 456 789'),
(4, '+34 644 567 890'),
(5, '+34 655 678 901');

-- Insert into Contract
INSERT INTO Contract (id_tenement, id_tenant, start_date, finish_date, price, contract_status) VALUES
('T001', 1, '2024-06-01', '2025-05-31', 650.00, 'ACTIVE'),
('T002', 2, '2023-01-15', '2023-12-31', 1200.00, 'EXPIRED'),
('T003', 3, '2024-09-01', '2025-08-31', 950.00, 'PENDING'),
('T004', 4, '2024-03-01', '2025-02-28', 800.00, 'ACTIVE'),
('T005', 5, '2022-07-01', '2023-06-30', 500.00, 'EXPIRED');