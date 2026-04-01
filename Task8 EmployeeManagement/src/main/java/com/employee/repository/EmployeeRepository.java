package com.employee.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.employee.model.Employee;

@Component
public class EmployeeRepository {

    private List<Employee> employeeList = new ArrayList<>();

    // Create
    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    // Read all
    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    // Read by ID
    public Employee findById(int id) {
        for (Employee emp : employeeList) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return null;
    }

    // Update
    public boolean updateEmployee(int id, String name, String department) {
        Employee emp = findById(id);
        if (emp != null) {
            emp.setName(name);
            emp.setDepartment(department);
            return true;
        }
        return false;
    }

    // Delete
    public boolean deleteEmployee(int id) {
        Employee emp = findById(id);
        if (emp != null) {
            employeeList.remove(emp);
            return true;
        }
        return false;
    }
}