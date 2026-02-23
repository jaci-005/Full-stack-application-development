CREATE VIEW Daily_Activity_Report AS
SELECT 
    CAST(ChangedAt AS DATE) AS LogDate,
    Action,
    COUNT(*) AS TotalChanges
FROM Audit_Logs
GROUP BY CAST(ChangedAt AS DATE), Action;