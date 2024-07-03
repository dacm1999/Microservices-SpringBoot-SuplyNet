package com.dacm.dev.userservice.infrastructure.config;//package com.dacm.dev.userservice.infrastructure.config;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.stream.Collectors;


import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ObjectValidator {

    private final Validator validator;

    @SneakyThrows
    public <T> T validate (T object) {
        Set<ConstraintViolation<T>> errors = validator.validate(object);
        if(errors.isEmpty())
            return object;
        else {
            String message = errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            throw new Exception(message);
//            throw new CustomException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, message, LocalDateTime.now());
        }
    }
}
