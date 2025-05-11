package com.reliaquest.api.service;

import com.reliaquest.api.client.EmployeeClient;
import com.reliaquest.api.exception.EmployeeNotFoundException;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeClient employeeClient;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;
    private List<Employee> employeeList;
    private UUID validId;

    @BeforeEach
    void setUp() {
        validId = UUID.randomUUID();
        employee1 = new Employee(validId.toString(), "John Doe", 50000, 30, "Developer", "john@company.com");
        employee2 = new Employee(UUID.randomUUID().toString(), "Jane Smith", 60000, 35, "Manager", "jane@company.com");
        employeeList = Arrays.asList(employee1, employee2);
    }

    @Test
    void getAllEmployees_ShouldReturnAllEmployees() {
        when(employeeClient.getAllEmployees()).thenReturn(employeeList);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
        verify(employeeClient).getAllEmployees();
    }

    @Test
    void searchByName_ShouldReturnMatchingEmployees() {
        when(employeeClient.getAllEmployees()).thenReturn(employeeList);

        List<Employee> result = employeeService.searchByName("John");

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(employeeClient).getAllEmployees();
    }

    @Test
    void searchByName_ShouldBeCaseInsensitive() {
        when(employeeClient.getAllEmployees()).thenReturn(employeeList);

        List<Employee> result = employeeService.searchByName("john");

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void getById_ShouldReturnEmployee_WhenFound() {
        when(employeeClient.getEmployeeById(validId.toString())).thenReturn(Optional.of(employee1));

        Employee result = employeeService.getById(validId);

        assertEquals("John Doe", result.getName());
        verify(employeeClient).getEmployeeById(validId.toString());
    }

    @Test
    void getById_ShouldThrowException_WhenNotFound() {
        UUID invalidId = UUID.randomUUID();
        when(employeeClient.getEmployeeById(invalidId.toString())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getById(invalidId));
    }

    @Test
    void getHighestSalary_ShouldReturnHighestSalary() {
        when(employeeClient.getAllEmployees()).thenReturn(employeeList);

        int result = employeeService.getHighestSalary();

        assertEquals(60000, result);
        verify(employeeClient).getAllEmployees();
    }

    @Test
    void getHighestSalary_ShouldReturnZero_WhenNoEmployees() {
        when(employeeClient.getAllEmployees()).thenReturn(List.of());

        int result = employeeService.getHighestSalary();

        assertEquals(0, result);
    }

    @Test
    void getTop10Earners_ShouldReturnSortedNames() {
        when(employeeClient.getAllEmployees()).thenReturn(employeeList);

        List<String> result = employeeService.getTop10Earners();

        assertEquals(2, result.size());
        assertEquals("Jane Smith", result.get(0)); // Higher salary
        assertEquals("John Doe", result.get(1));   // Lower salary
    }

    @Test
    void getTop10Earners_ShouldLimitToTenResults() {
        List<Employee> manyEmployees = createEmployeeListWithSize(15);
        when(employeeClient.getAllEmployees()).thenReturn(manyEmployees);

        List<String> result = employeeService.getTop10Earners();

        assertEquals(10, result.size());
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setName("New Employee");
        request.setSalary(55000);

        Employee newEmployee = new Employee(UUID.randomUUID().toString(), "New Employee", 55000, 25, "Developer", "new@company.com");
        when(employeeClient.createEmployee(request)).thenReturn(newEmployee);

        Employee result = employeeService.createEmployee(request);

        assertEquals("New Employee", result.getName());
        assertEquals(55000, result.getSalary());
        verify(employeeClient).createEmployee(request);
    }

    @Test
    void deleteById_ShouldReturnTrue_WhenSuccessful() {
        String employeeId = "123";
        when(employeeClient.deleteById(employeeId)).thenReturn(true);

        boolean result = employeeService.deleteById(employeeId);

        assertTrue(result);
        verify(employeeClient).deleteById(employeeId);
    }

    @Test
    void deleteById_ShouldReturnFalse_WhenUnsuccessful() {
        String employeeId = "456";
        when(employeeClient.deleteById(employeeId)).thenReturn(false);

        boolean result = employeeService.deleteById(employeeId);

        assertFalse(result);
        verify(employeeClient).deleteById(employeeId);
    }

    private List<Employee> createEmployeeListWithSize(int size) {
        return Arrays.stream(new Employee[size])
                .map(e -> new Employee(
                        UUID.randomUUID().toString(),
                        "Employee " + UUID.randomUUID(),
                        (int)(Math.random() * 100000),
                        30,
                        "Title",
                        "email@company.com"))
                .toList();
    }
}
