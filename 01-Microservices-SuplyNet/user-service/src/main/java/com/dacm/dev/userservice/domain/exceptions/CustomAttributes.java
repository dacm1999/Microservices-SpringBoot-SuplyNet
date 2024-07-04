package com.dacm.dev.userservice.domain.exceptions;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new HashMap<>();
        Throwable throwable = super.getError(request);
        if(throwable instanceof CustomException) {
            CustomException customException = (CustomException) throwable;
            errorAttributes.put("code", customException.getCode());
            errorAttributes.put("message", customException.getMessage());
            errorAttributes.put("status", customException.getStatus());
            errorAttributes.put("timestamp", customException.getTimestamp());
        }
        return errorAttributes;
    }
}
