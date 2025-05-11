package com.reliaquest.api.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String id;
    private String name;
    private int salary;
    private int age;
    private String title;
    private String email;
}
