package com.dacm.dev.userservice.domain.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
public class CustomException extends Exception{

    private int code;
    private String message;
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    public CustomException(int code, String message, HttpStatus status, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }
}
