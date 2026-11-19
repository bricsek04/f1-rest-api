package com.f1.f1_rest_api.repository;

import com.f1.f1_rest_api.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    List<DriverEntity> findByConstructorId(Long constructorId);
    boolean existsByPermanentNumber(Integer permanentNumber);
    boolean existsByPermanentNumberAndIdNot(Integer permanentNumber, Long id);
}