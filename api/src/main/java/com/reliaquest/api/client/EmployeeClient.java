package com.reliaquest.api.client;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeCreateRequest;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class EmployeeClient {

    private final String BASE_URL = "http://localhost:8112/api/v1/employee";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Employee> getAllEmployees() {
        Map<String, Object> response = restTemplate.getForObject(BASE_URL, Map.class);
        return extractEmployeeList(response);
    }

    public Optional<Employee> getEmployeeById(String id) {
        try {
            Map<String, Object> response = restTemplate.getForObject(BASE_URL + "/" + id, Map.class);
            return Optional.of(parseEmployee((Map<String, Object>) response.get("data")));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    public Employee createEmployee(EmployeeCreateRequest request) {
        Map<String, Object> response = restTemplate.postForObject(BASE_URL, request, Map.class);
        return parseEmployee((Map<String, Object>) response.get("data"));
    }


    public boolean deleteById(String id) {
        try {
            Optional<Employee> employee = getEmployeeById(id);
            if (employee.isEmpty() || employee.get().getName() == null) {
                return false;
            }

            Map<String, String> requestBody = new HashMap<>();
            String name = employee.get().getName();
            requestBody.put("name", name);

            log.info("name is: ", name);

            ResponseEntity<Map> response = restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.DELETE,
                    new HttpEntity<>(requestBody),
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            return responseBody != null && Boolean.TRUE.equals(responseBody.get("data"));
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    private List<Employee> extractEmployeeList(Map<String, Object> response) {
        List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");
        List<Employee> result = new ArrayList<>();
        for (Map<String, Object> emp : data) {
            result.add(parseEmployee(emp));
        }
        return result;
    }

    private Employee parseEmployee(Map<String, Object> map) {
        return new Employee(
                (String) map.get("id"),
                (String) map.get("employee_name"),
                (Integer) map.get("employee_salary") ,
                (Integer) map.get("employee_age"),
                (String) map.get("employee_title"),
                (String) map.get("employee_email"));
    }
}
