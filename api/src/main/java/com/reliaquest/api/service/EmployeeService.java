package com.reliaquest.api.service;

import com.reliaquest.api.client.EmployeeClient;
import com.reliaquest.api.exception.EmployeeNotFoundException;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeCreateRequest;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeClient client;

    public List<Employee> getAllEmployees() {
        return client.getAllEmployees();
    }

    public List<Employee> searchByName(String fragment) {
        return client.getAllEmployees().stream()
                .filter(e -> e.getName().toLowerCase().contains(fragment.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Employee getById(UUID id) {
        return client.getEmployeeById(id.toString())
                .orElseThrow(() -> new EmployeeNotFoundException(id.toString()));
    }

    public int getHighestSalary() {
        return client.getAllEmployees().stream()
                .mapToInt(Employee::getSalary)
                .max()
                .orElse(0);
    }

    public List<String> getTop10Earners() {
        return client.getAllEmployees().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getSalary(), e1.getSalary()))
                .limit(10)
                .map(Employee::getName)
                .collect(Collectors.toList());
    }

    public Employee createEmployee(EmployeeCreateRequest request) {
        return client.createEmployee(request);
    }

    public boolean deleteById(String name) {
        return client.deleteById(name);
    }
}
