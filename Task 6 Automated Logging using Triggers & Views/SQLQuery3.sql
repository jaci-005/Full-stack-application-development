CREATE TRIGGER trg_employee_audit
ON Employees
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- INSERT operation
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO Audit_Logs (TableName, Action, NewData)
        SELECT 
            'Employees',
            'INSERT',
            (SELECT * FROM inserted FOR JSON PATH);
    END

    -- UPDATE operation
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO Audit_Logs (TableName, Action, OldData, NewData)
        SELECT 
            'Employees',
            'UPDATE',
            (SELECT * FROM deleted FOR JSON PATH),
            (SELECT * FROM inserted FOR JSON PATH);
    END
END;