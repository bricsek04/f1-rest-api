package com.f1.f1_rest_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, length = 3)
    private String code;

    @Column(nullable = false, unique = true)
    private Integer permanentNumber;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private Integer points;

    @Column(nullable = false)
    private Integer superLicencePenaltyPoints;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "constructor_id", nullable = false)
    private ConstructorEntity constructor;

    @Version
    private Long version;
}