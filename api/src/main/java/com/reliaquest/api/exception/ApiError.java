package com.reliaquest.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class ApiError {
    private int status;
    private String message;
}
