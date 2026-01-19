package com.hospital.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
    private boolean success;
    private int status;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

}
