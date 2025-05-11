package com.reliaquest.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeCreateRequest;
import com.reliaquest.api.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(EmployeeController.class)
//class EmployeeControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private EmployeeService employeeService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private Employee testEmployee;
//    private List<Employee> employeeList;
//    private EmployeeCreateRequest createRequest;
//
//    @BeforeEach
//    void setUp() {
//        testEmployee = new Employee();
//        testEmployee.setId("1");
//        testEmployee.setName("John Doe");
//        testEmployee.setSalary(50000);
//
//        employeeList = Arrays.asList(testEmployee);
//
//        createRequest = new EmployeeCreateRequest();
//        createRequest.setEmployeeName("John Doe");
//        createRequest.setEmployeeSalary(50000);
//    }
//
//    @Test
//    void getAllEmployees_ShouldReturnListOfEmployees() throws Exception {
//        when(employeeService.getAllEmployees()).thenReturn(employeeList);
//
//        mockMvc.perform(get("/api/v1/employee"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value("1"))
//                .andExpect(jsonPath("$[0].name").value("John Doe"));
//    }
//
//    @Test
//    void getEmployeesByNameSearch_ShouldReturnMatchingEmployees() throws Exception {
//        when(employeeService.searchByName(anyString())).thenReturn(employeeList);
//
//        mockMvc.perform(get("/api/v1/employee/search/John"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].name").value("John Doe"));
//    }
//
//    @Test
//    void getEmployeeById_ShouldReturnEmployee() throws Exception {
//        when(employeeService.getById(anyString())).thenReturn(testEmployee);
//
//        mockMvc.perform(get("/api/v1/employee/{id}", "1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value("1"))
//                .andExpect(jsonPath("$.name").value("John Doe"));
//    }
//
//    @Test
//    void getHighestSalaryOfEmployees_ShouldReturnHighestSalary() throws Exception {
//        when(employeeService.getHighestSalary()).thenReturn(100000);
//
//        mockMvc.perform(get("/api/v1/employee/highestSalary"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string("100000"));
//    }
//
//    @Test
//    void getTopTenHighestEarningEmployeeNames_ShouldReturnListOfNames() throws Exception {
//        List<String> topEarners = Arrays.asList("John Doe", "Jane Smith");
//        when(employeeService.getTop10Earners()).thenReturn(topEarners);
//
//        mockMvc.perform(get("/api/v1/employee/topTenHighestEarningEmployeeNames"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0]").value("John Doe"))
//                .andExpect(jsonPath("$[1]").value("Jane Smith"));
//    }
//
//    @Test
//    void createEmployee_ShouldReturnCreatedEmployee() throws Exception {
//        when(employeeService.createEmployee(any(EmployeeCreateRequest.class))).thenReturn(testEmployee);
//
//        mockMvc.perform(post("/api/v1/employee")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(createRequest)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("John Doe"));
//    }
//
//    @Test
//    void deleteEmployeeById_ShouldReturnSuccess() throws Exception {
//        when(employeeService.deleteByName(anyString())).thenReturn(true);
//
//        mockMvc.perform(delete("/api/v1/employee/{id}", "1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("true"));
//    }
//}


import java.util.Collections;
import java.util.UUID;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee testEmployee;
    private List<Employee> employeeList;
    private EmployeeCreateRequest createRequest;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee();
        testEmployee.setId("1");
        testEmployee.setName("John Doe");
        testEmployee.setSalary(50000);

        employeeList = Arrays.asList(testEmployee);

        createRequest = new EmployeeCreateRequest();
        createRequest.setEmployeeName("John Doe");
        createRequest.setEmployeeSalary(50000);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/api/v1/employee")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void getAllEmployees_WhenEmpty_ShouldReturnEmptyList() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/employee")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getAllEmployees_WhenServiceThrowsException_ShouldReturn500() throws Exception {
        when(employeeService.getAllEmployees()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/v1/employee")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getEmployeesByNameSearch_ShouldReturnMatchingEmployees() throws Exception {
        when(employeeService.searchByName(anyString())).thenReturn(employeeList);

        mockMvc.perform(get("/api/v1/employee/search/John")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void getEmployeesByNameSearch_WhenNoMatch_ShouldReturnEmptyList() throws Exception {
        when(employeeService.searchByName(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/employee/search/NonexistentName")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() throws Exception {
        when(employeeService.getById(anyString())).thenReturn(testEmployee);

        mockMvc.perform(get("/api/v1/employee/{id}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getEmployeeById_WhenNotFound_ShouldReturn404() throws Exception {

        UUID id = UUID.randomUUID();
        when(employeeService.getById(id)).thenReturn(null);

        mockMvc.perform(get("/api/v1/employee/{id}", id.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getHighestSalaryOfEmployees_ShouldReturnHighestSalary() throws Exception {
        when(employeeService.getHighestSalary()).thenReturn(100000);

        mockMvc.perform(get("/api/v1/employee/highestSalary")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("100000"));
    }

    @Test
    void getHighestSalaryOfEmployees_WhenNoEmployees_ShouldReturn0() throws Exception {
        when(employeeService.getHighestSalary()).thenReturn(0);

        mockMvc.perform(get("/api/v1/employee/highestSalary")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_ShouldReturnListOfNames() throws Exception {
        List<String> topEarners = Arrays.asList("John Doe", "Jane Smith");
        when(employeeService.getTop10Earners()).thenReturn(topEarners);

        mockMvc.perform(get("/api/v1/employee/topTenHighestEarningEmployeeNames")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("John Doe"))
                .andExpect(jsonPath("$[1]").value("Jane Smith"));
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_WhenEmpty_ShouldReturnEmptyList() throws Exception {
        when(employeeService.getTop10Earners()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/employee/topTenHighestEarningEmployeeNames")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() throws Exception {
        when(employeeService.createEmployee(any(EmployeeCreateRequest.class))).thenReturn(testEmployee);

        mockMvc.perform(post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void createEmployee_WithInvalidData_ShouldReturn400() throws Exception {
        EmployeeCreateRequest invalidRequest = new EmployeeCreateRequest();
        // Missing required fields

        mockMvc.perform(post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEmployee_WithNegativeSalary_ShouldReturn400() throws Exception {
        createRequest.setEmployeeSalary(-1000);

        mockMvc.perform(post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteEmployeeById_ShouldReturnSuccess() throws Exception {
        when(employeeService.deleteById(anyString())).thenReturn(true);

        mockMvc.perform(delete("/api/v1/employee/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void deleteEmployeeById_WhenNotFound_ShouldReturnFalse() throws Exception {
        when(employeeService.deleteById(anyString())).thenReturn(false);

        mockMvc.perform(delete("/api/v1/employee/{id}", "999"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
