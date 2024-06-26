package com.dacm.dev.authorizationservice.infrastructure.adapters.output.persistence.entity;

import com.dacm.dev.authorizationservice.domain.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private RoleName role;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
//    private List<GrantedPermission> permissions;
//

    @Override
    public String getAuthority() {
        return role.name();
    }
}
