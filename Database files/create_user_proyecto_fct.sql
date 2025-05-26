USE proyecto_final_fct;

CREATE USER app_user@'%' IDENTIFIED BY 'ReallySecurePassword1234#';
GRANT SELECT, INSERT, ALTER, UPDATE, DELETE, EXECUTE ON proyecto_final_fct.* TO app_user@'%';