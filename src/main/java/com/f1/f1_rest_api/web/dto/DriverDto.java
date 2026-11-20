package com.f1.f1_rest_api.web.dto;

import com.f1.f1_rest_api.entity.DriverEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "drivers", itemRelation = "driver")
public record DriverDto(
        Long id,

        @NotBlank(message = "Full name must not be blank")
        String fullName,

        @NotBlank(message = "Code must not be blank")
        @Size(min = 3, max = 3, message = "Code must be exactly 3 characters")
        String code,

        @NotNull(message = "Permanent number must not be null")
        @Min(value = 1, message = "Permanent number must be between 1 and 99")
        @Max(value = 99, message = "Permanent number must be between 1 and 99")
        Integer permanentNumber,

        @NotBlank(message = "Nationality must not be blank")
        String nationality,

        @NotNull(message = "Points must not be null")
        @Min(value = 0, message = "Points must be at least 0")
        Integer points,

        @NotNull(message = "Super licence penalty points must not be null")
        @Min(value = 0, message = "Super licence penalty points must be between 0 and 12")
        @Max(value = 12, message = "Super licence penalty points must be between 0 and 12")
        Integer superLicencePenaltyPoints,

        @NotNull(message = "Constructor ID must not be null")
        Long constructorId
) {
    public static DriverDto from(DriverEntity entity) {
        return new DriverDto(
                entity.getId(),
                entity.getFullName(),
                entity.getCode(),
                entity.getPermanentNumber(),
                entity.getNationality(),
                entity.getPoints(),
                entity.getSuperLicencePenaltyPoints(),
                entity.getConstructor() != null ? entity.getConstructor().getId() : null
        );
    }
}