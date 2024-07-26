package com.dacm.dev.userservice.infrastructure.adapters.output.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean expired = false;
    private boolean locked = false;
    private boolean credentialsExpired = false;
    private boolean disabled = false;

//    public User(UserDetails user) {
//
//        this.setUsername(user.getUsername());
//        this.setPassword(user.getPassword());
//
//    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        //        return null;
        //        return roles.stream()
        //                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
        //                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }
}
