package com.f1.f1_rest_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "circuits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CircuitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Integer lengthMeters;

    @Column(nullable = false)
    private Integer laps;

    private String lapRecordTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lap_record_driver_id")
    private DriverEntity lapRecordDriver;

    @Version
    private Long version;
}