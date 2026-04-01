package com.employee.EmployeeManagement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;

public class AppTest {

    private EmployeeRepository repository;
    private EmployeeService service;

    @BeforeEach
    public void setUp() {
        // Use Spring context to wire dependencies
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("beans.xml");
        service = context.getBean(EmployeeService.class);
        repository = context.getBean(EmployeeRepository.class);
    }

    // ==================== MODEL TESTS ====================

    @Test
    @DisplayName("Employee constructor and getters work correctly")
    public void testEmployeeCreation() {
        Employee emp = new Employee(1, "John", "IT");
        assertEquals(1, emp.getId());
        assertEquals("John", emp.getName());
        assertEquals("IT", emp.getDepartment());
    }

    @Test
    @DisplayName("Employee setters update fields correctly")
    public void testEmployeeSetters() {
        Employee emp = new Employee(1, "John", "IT");
        emp.setName("Jane");
        emp.setDepartment("HR");
        assertEquals("Jane", emp.getName());
        assertEquals("HR", emp.getDepartment());
    }

    @Test
    @DisplayName("Employee toString returns formatted string")
    public void testEmployeeToString() {
        Employee emp = new Employee(1, "John", "IT");
        String result = emp.toString();
        assertTrue(result.contains("1"));
        assertTrue(result.contains("John"));
        assertTrue(result.contains("IT"));
    }

    @Test
    @DisplayName("Employee no-arg constructor creates empty employee")
    public void testEmployeeNoArgConstructor() {
        Employee emp = new Employee();
        assertEquals(0, emp.getId());
        assertNull(emp.getName());
        assertNull(emp.getDepartment());
    }

    // ==================== REPOSITORY TESTS ====================

    @Test
    @DisplayName("Repository adds employee and retrieves all")
    public void testAddAndGetAll() {
        repository.addEmployee(new Employee(1, "Alice", "IT"));
        repository.addEmployee(new Employee(2, "Bob", "HR"));
        List<Employee> all = repository.getAllEmployees();
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("Repository finds employee by ID")
    public void testFindById() {
        repository.addEmployee(new Employee(10, "Charlie", "Finance"));
        Employee found = repository.findById(10);
        assertNotNull(found);
        assertEquals("Charlie", found.getName());
        assertEquals("Finance", found.getDepartment());
    }

    @Test
    @DisplayName("Repository returns null for non-existent ID")
    public void testFindByIdNotFound() {
        Employee found = repository.findById(999);
        assertNull(found);
    }

    @Test
    @DisplayName("Repository updates existing employee")
    public void testUpdateEmployee() {
        repository.addEmployee(new Employee(20, "Dave", "Sales"));
        boolean result = repository.updateEmployee(20, "David", "Marketing");
        assertTrue(result);
        Employee updated = repository.findById(20);
        assertEquals("David", updated.getName());
        assertEquals("Marketing", updated.getDepartment());
    }

    @Test
    @DisplayName("Repository update returns false for non-existent ID")
    public void testUpdateEmployeeNotFound() {
        boolean result = repository.updateEmployee(999, "Ghost", "None");
        assertFalse(result);
    }

    @Test
    @DisplayName("Repository deletes existing employee")
    public void testDeleteEmployee() {
        repository.addEmployee(new Employee(30, "Eve", "QA"));
        boolean result = repository.deleteEmployee(30);
        assertTrue(result);
        assertNull(repository.findById(30));
    }

    @Test
    @DisplayName("Repository delete returns false for non-existent ID")
    public void testDeleteEmployeeNotFound() {
        boolean result = repository.deleteEmployee(999);
        assertFalse(result);
    }

    // ==================== SERVICE TESTS ====================

    @Test
    @DisplayName("Service creates employee and retrieves it")
    public void testServiceCreateAndGet() {
        service.createEmployee(100, "Frank", "Engineering");
        Employee emp = service.getEmployeeById(100);
        assertNotNull(emp);
        assertEquals("Frank", emp.getName());
        assertEquals("Engineering", emp.getDepartment());
    }

    @Test
    @DisplayName("Service returns all employees")
    public void testServiceGetAll() {
        service.createEmployee(200, "Grace", "Design");
        service.createEmployee(201, "Hank", "DevOps");
        List<Employee> all = service.getAllEmployees();
        assertTrue(all.size() >= 2);
    }

    @Test
    @DisplayName("Service updates employee successfully")
    public void testServiceUpdate() {
        service.createEmployee(300, "Ivy", "Support");
        boolean result = service.updateEmployee(300, "Ivy M.", "Tech Support");
        assertTrue(result);
        Employee updated = service.getEmployeeById(300);
        assertEquals("Ivy M.", updated.getName());
        assertEquals("Tech Support", updated.getDepartment());
    }

    @Test
    @DisplayName("Service deletes employee successfully")
    public void testServiceDelete() {
        service.createEmployee(400, "Jack", "Legal");
        boolean result = service.deleteEmployee(400);
        assertTrue(result);
        assertNull(service.getEmployeeById(400));
    }

    @Test
    @DisplayName("Service search returns null for non-existent ID")
    public void testServiceSearchNotFound() {
        Employee emp = service.getEmployeeById(9999);
        assertNull(emp);
    }

    // ==================== INTEGRATION TEST ====================

    @Test
    @DisplayName("Spring context loads and beans are wired correctly")
    public void testSpringContextLoads() {
        assertNotNull(service);
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Full CRUD workflow via service layer")
    public void testFullCRUDWorkflow() {
        // Create
        service.createEmployee(500, "TestUser", "TestDept");
        Employee created = service.getEmployeeById(500);
        assertNotNull(created);
        assertEquals("TestUser", created.getName());

        // Update
        service.updateEmployee(500, "UpdatedUser", "UpdatedDept");
        Employee updated = service.getEmployeeById(500);
        assertEquals("UpdatedUser", updated.getName());
        assertEquals("UpdatedDept", updated.getDepartment());

        // Delete
        boolean deleted = service.deleteEmployee(500);
        assertTrue(deleted);
        assertNull(service.getEmployeeById(500));
    }
}
