package com.dacm.dev.userservice.domain.dtos.response;


import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponseDto extends Response {

    public ApiResponseDto(Integer code, String message, HttpStatus status, LocalDateTime timestamp) {
        super( code,message, status, timestamp, null);
    }

    public ApiResponseDto(Integer code, String message, HttpStatus status, LocalDateTime timestamp, Object data) {
        super( code,message, status, timestamp, data);
    }
}
