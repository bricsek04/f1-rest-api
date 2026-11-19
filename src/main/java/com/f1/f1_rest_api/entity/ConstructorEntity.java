package com.f1.f1_rest_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "constructors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstructorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String baseLocation;

    @Column(nullable = false)
    private String engineSupplier;

    @Column(nullable = false)
    private String teamPrincipalUsername;

    @Column(nullable = false)
    private Integer championshipPoints;

    @OneToMany(mappedBy = "constructor", fetch = FetchType.EAGER)
    @Builder.Default
    private List<DriverEntity> drivers = new ArrayList<>();

    @Version
    private Long version;
}