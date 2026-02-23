CREATE DATABASE OrderManagementDB; 
GO 
USE OrderManagementDB; 
GO 
Create Customers Table 
CREATE TABLE Customers ( 
CustomerID INT PRIMARY KEY, 
CustomerName VARCHAR(50), 
Email VARCHAR(50) 
); 

CREATE TABLE Products ( 
ProductID INT PRIMARY KEY, 
ProductName VARCHAR(50), 
Price DECIMAL(10,2) 
); 

CREATE TABLE Orders ( 
OrderID INT PRIMARY KEY, 
CustomerID INT, 
ProductID INT, 
Quantity INT, 
OrderDate DATE, 
FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID), 
FOREIGN KEY (ProductID) REFERENCES Products(ProductID) 
); 
 

INSERT INTO Customers VALUES 
(1, 'Alice', 'alice@gmail.com'), 
(2, 'Bob', 'bob@gmail.com'), 
(3, 'Charlie', 'charlie@gmail.com'); 

INSERT INTO Products VALUES 
(101, 'Laptop', 60000), 
(102, 'Mobile', 25000), 
(103, 'Headphones', 3000); 

INSERT INTO Orders VALUES 
(1001, 1, 101, 1, '2024-01-10'), 
(1002, 1, 102, 2, '2024-01-12'), 
(1003, 2, 103, 3, '2024-01-15'), 
(1004, 3, 101, 1, '2024-01-20'); 
 
SELECT  
c.CustomerName, 
p.ProductName, 
o.Quantity, 
o.OrderDate 
FROM Orders o 
INNER JOIN Customers c ON o.CustomerID = c.CustomerID 
INNER JOIN Products p ON o.ProductID = p.ProductID 
ORDER BY o.OrderDate; 
 
SELECT  
o.OrderID, 
c.CustomerName, 
(o.Quantity * p.Price) AS OrderValue 
FROM Orders o 
JOIN Customers c ON o.CustomerID = c.CustomerID 
JOIN Products p ON o.ProductID = p.ProductID 
WHERE (o.Quantity * p.Price) = ( 
SELECT MAX(o.Quantity * p.Price) 
FROM Orders o 
JOIN Products p ON o.ProductID = p.ProductID 
); 

SELECT TOP 1 
c.CustomerName, 
COUNT(o.OrderID) AS TotalOrders 
FROM Customers c 
JOIN Orders o ON c.CustomerID = o.CustomerID 
GROUP BY c.CustomerName 
ORDER BY TotalOrders DESC;