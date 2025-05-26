USE proyecto_final_fct;

SET GLOBAL event_scheduler = ON; -- I turn on this variable so the event can actually trigger
DELIMITER //

-- Procedure that is used when the event triggers
DROP PROCEDURE sp_expired_contract//
CREATE PROCEDURE sp_expired_contract()
BEGIN
	UPDATE contract SET contract_status = 'EXPIRED' WHERE finish_date > CURDATE() AND contract_status != 'EXPIRED';
END//

-- This is an event that triggers every day and checks every contract that has an expired date
CREATE EVENT ev_check_expired_contracts 
ON SCHEDULE EVERY 1 DAY STARTS TIMESTAMP(CURDATE() + INTERVAL 1 DAY) -- This makes it so the event triggers at midnight
DO CALL sp_expired_contract();