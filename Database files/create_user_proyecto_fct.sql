USE proyecto_final_fct;
select * from mysql.user;

CREATE USER app_user@'%' IDENTIFIED BY 'ReallySecurePassword1234#';
GRANT USAGE, SELECT, INSERT, ALTER, UPDATE, DELETE, EXECUTE ON proyecto_final_fct.* TO app_user@'%';

CREATE USER app_user@'localhost' IDENTIFIED BY 'ReallySecurePassword1234#';
GRANT USAGE, SELECT, INSERT, ALTER, UPDATE, DELETE, EXECUTE ON proyecto_final_fct.* TO app_user@'localhost';

select * from Landlord;


select * from Landlord_Phone_Number;

SELECT t.* FROM tenement t LEFT JOIN contract c ON c.id_tenement = t.id WHERE t.id NOT IN(SELECT id_tenement FROM contract WHERE contract_status IN ('ACTIVE', 'PENDING'));
SELECT * FROM contract;
SELECT t.*, h.name FROM house_type h LEFT JOIN tenement t ON h.id = t.type LEFT JOIN contract c ON c.id_tenement = t.id WHERE t.id NOT IN(SELECT id_tenement FROM contract WHERE contract_status IN ('ACTIVE', 'PENDING'));
SELECT l.id, l.name, SUM(c.price) AS total_gains, AVG(c.price) AS average_rent FROM landlord l LEFT JOIN tenement t ON l.id = t.id_landlord LEFT JOIN contract c ON t.id = c.id_tenement WHERE c.contract_status = 'ACTIVE' GROUP BY l.id;
SELECT t.*, h.name FROM house_type h JOIN tenement t ON h.id = t.type JOIN contract c ON c.id_tenement = t.id JOIN tenant te ON te.id = c.id_tenant  WHERE te.has_pet = 1 AND c.contract_status = 'ACTIVE';
SELECT h.name, COUNT(t.id) AS total_rents FROM house_type h JOIN tenement t ON h.id = t.type JOIN contract c ON c.id_tenement = t.id WHERE c.contract_status = 'ACTIVE' GROUP BY h.name ORDER BY total_rents DESC LIMIT 1;