package com.f1.f1_rest_api.repository;

import com.f1.f1_rest_api.entity.CircuitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircuitRepository extends JpaRepository<CircuitEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
    List<CircuitEntity> findByLapRecordDriverId(Long driverId);
}