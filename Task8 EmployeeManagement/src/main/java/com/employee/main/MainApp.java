package com.employee.main;

import java.util.List;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;

public class MainApp {

    public static void main(String[] args) {

        // Load Spring container
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("beans.xml");

        // Get EmployeeService bean from Spring container
        EmployeeService service = context.getBean(EmployeeService.class);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("============================================");
        System.out.println("   Employee Management System (Spring DI)   ");
        System.out.println("============================================");

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (1-6).");
                continue;
            }

            switch (choice) {
                case 1:
                    addEmployee(scanner, service);
                    break;
                case 2:
                    service.displayEmployees();
                    break;
                case 3:
                    searchEmployee(scanner, service);
                    break;
                case 4:
                    updateEmployee(scanner, service);
                    break;
                case 5:
                    deleteEmployee(scanner, service);
                    break;
                case 6:
                    running = false;
                    System.out.println("\nExiting Employee Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 6.");
            }
        }

        scanner.close();
        context.close();
    }

    private static void addEmployee(Scanner scanner, EmployeeService service) {
        System.out.print("\nEnter Employee ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID! Must be a number.");
            return;
        }

        // Check if ID already exists
        if (service.getEmployeeById(id) != null) {
            System.out.println("Employee with ID " + id + " already exists!");
            return;
        }

        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }

        System.out.print("Enter Department: ");
        String dept = scanner.nextLine().trim();
        if (dept.isEmpty()) {
            System.out.println("Department cannot be empty!");
            return;
        }

        service.createEmployee(id, name, dept);
    }

    private static void searchEmployee(Scanner scanner, EmployeeService service) {
        System.out.print("\nEnter Employee ID to search: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID! Must be a number.");
            return;
        }

        Employee emp = service.getEmployeeById(id);
        if (emp != null) {
            System.out.println("\nEmployee Found:");
            System.out.println("+--------+-----------------+-----------------+");
            System.out.println("| ID     | Name            | Department      |");
            System.out.println("+--------+-----------------+-----------------+");
            System.out.println(emp);
            System.out.println("+--------+-----------------+-----------------+");
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private static void updateEmployee(Scanner scanner, EmployeeService service) {
        System.out.print("\nEnter Employee ID to update: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID! Must be a number.");
            return;
        }

        Employee existing = service.getEmployeeById(id);
        if (existing == null) {
            System.out.println("Employee with ID " + id + " not found.");
            return;
        }

        System.out.println("Current details: " + existing.getName()
                + " | " + existing.getDepartment());

        System.out.print("Enter new Name (press Enter to keep current): ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = existing.getName();
        }

        System.out.print("Enter new Department (press Enter to keep current): ");
        String dept = scanner.nextLine().trim();
        if (dept.isEmpty()) {
            dept = existing.getDepartment();
        }

        if (service.updateEmployee(id, name, dept)) {
            System.out.println("Employee updated successfully!");
        } else {
            System.out.println("Failed to update employee.");
        }
    }

    private static void deleteEmployee(Scanner scanner, EmployeeService service) {
        System.out.print("\nEnter Employee ID to delete: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID! Must be a number.");
            return;
        }

        Employee existing = service.getEmployeeById(id);
        if (existing == null) {
            System.out.println("Employee with ID " + id + " not found.");
            return;
        }

        System.out.print("Are you sure you want to delete '"
                + existing.getName() + "'? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            if (service.deleteEmployee(id)) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Failed to delete employee.");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }
}