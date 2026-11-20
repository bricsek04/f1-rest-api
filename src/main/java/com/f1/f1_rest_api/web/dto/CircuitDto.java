package com.f1.f1_rest_api.web.dto;

import com.f1.f1_rest_api.entity.CircuitEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "circuits", itemRelation = "circuit")
public record CircuitDto (
    Long id,

    @NotBlank(message = "Name must be not blank")
    String name,

    @NotBlank(message = "Country must be not blank")
    String country,

    @NotBlank(message = "Length in meters must not be null")
    @Min(value = 1, message = "Length in meters mut be at least 1")
    Integer lengthMeters,

    @NotNull(message = "Laps must not be null")
    @Min(value = 1, message = "Laps must be at least 1")
    Integer laps,

    String lapRecordTime,

    Long lapRecordDriverId
) {
    public static CircuitDto from(CircuitEntity entity) {
        return new CircuitDto(
                entity.getId(),
                entity.getName(),
                entity.getCountry(),
                entity.getLengthMeters(),
                entity.getLaps(),
                entity.getLapRecordTime(),
                entity.getLapRecordDriver() != null ? entity.getLapRecordDriver().getId() : null
        );
    }
}
