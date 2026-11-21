package com.f1.f1_rest_api.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DriverPatchDto(
        @Pattern(regexp = ".*\\S.*", message = "Full name must not be blank")
        String fullName,

        @Size(min = 3, max = 3, message = "Code must be exactly 3 characters")
        @Pattern(regexp = ".*\\S.*", message = "Code must not be blank")
        String code,

        @Min(value = 1, message = "Permanent number must be between 1 and 99")
        @Max(value = 99, message = "Permanent number must be between 1 and 99")
        Integer permanentNumber,

        @Pattern(regexp = ".*\\S.*", message = "Nationality must not be blank")
        String nationality,

        @Min(value = 0, message = "Points must be at least 0")
        Integer points,

        @Min(value = 0, message = "Super licence penalty points must be between 0 and 12")
        @Max(value = 12, message = "Super licence penalty points must be between 0 and 12")
        Integer superLicencePenaltyPoints,

        Long constructorId
) {
}