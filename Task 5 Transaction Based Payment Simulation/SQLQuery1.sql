CREATE DATABASE PaymentDB; 
GO 
USE PaymentDB; 
GO 


CREATE TABLE Accounts ( 
AccountId INT IDENTITY(1,1) PRIMARY KEY, 
AccountName VARCHAR(50) NOT NULL, 
AccountType VARCHAR(20) NOT NULL CHECK (AccountType IN 
('USER','MERCHANT')), 
Balance DECIMAL(12,2) NOT NULL CHECK (Balance >= 0) 
); 

INSERT INTO Accounts (AccountName, AccountType, Balance) 
VALUES 
('Kumar', 'USER', 5000.00), 
('Anita', 'USER', 2500.00), 
('AmazonStore', 'MERCHANT', 10000.00), 
('FlipkartStore', 'MERCHANT', 8000.00);

SELECT * FROM Accounts;