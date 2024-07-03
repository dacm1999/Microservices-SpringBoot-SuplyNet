package com.dacm.dev.userservice.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
//    private List<String> roles;

}