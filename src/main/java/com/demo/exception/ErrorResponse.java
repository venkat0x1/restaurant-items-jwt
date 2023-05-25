package com.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int statusCode;
    private String message;
    private String path;
}
