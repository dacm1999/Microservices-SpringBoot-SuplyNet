package com.dacm.dev.authorizationservice.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String path;

    private String httpMethod;

    private boolean permitAll;

    @Column(name = "module_id")
    private long moduleId;
}
