package com.reliaquest.api.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeCreateRequest {
    @NotBlank(message = "Employee name is required")
    private String name;

    @NotBlank(message = "Employee title is required")
    private String title;

    @Positive
    @NotNull(message = "Employee salary is required")
    private Integer salary;

    @Min(16)
    @Max(75)
    @NotNull(message = "Employee age is required")
    private Integer age;
}
