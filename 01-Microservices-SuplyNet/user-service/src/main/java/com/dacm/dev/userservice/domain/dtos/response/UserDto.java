package com.dacm.dev.userservice.domain.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;

}