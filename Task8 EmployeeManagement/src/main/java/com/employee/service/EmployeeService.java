package com.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@Component
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    // Create a new employee
    public void createEmployee(int id, String name, String dept) {
        Employee emp = new Employee(id, name, dept);
        repository.addEmployee(emp);
        System.out.println("Employee added successfully!");
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return repository.getAllEmployees();
    }

    // Display all employees in formatted table
    public void displayEmployees() {
        List<Employee> employees = repository.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("\nNo employees found.");
            return;
        }
        System.out.println("\n+--------+-----------------+-----------------+");
        System.out.println("| ID     | Name            | Department      |");
        System.out.println("+--------+-----------------+-----------------+");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
        System.out.println("+--------+-----------------+-----------------+");
        System.out.println("Total employees: " + employees.size());
    }

    // Search employee by ID
    public Employee getEmployeeById(int id) {
        return repository.findById(id);
    }

    // Update employee
    public boolean updateEmployee(int id, String name, String dept) {
        return repository.updateEmployee(id, name, dept);
    }

    // Delete employee
    public boolean deleteEmployee(int id) {
        return repository.deleteEmployee(id);
    }
}