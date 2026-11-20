package com.f1.f1_rest_api.web.dto;

import com.f1.f1_rest_api.entity.ConstructorEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "constructors", itemRelation = "constructor")
public record ConstructorDto(
        Long id,

        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Base location must not be blank")
        String baseLocation,

        @NotBlank(message = "Engine supplier must not be blank")
        String engineSupplier,

        @NotBlank(message = "Team principal username must not be blank")
        String teamPrincipalUsername,

        @NotNull(message = "Championship points must not be null")
        @Min(value = 0, message = "Championship points must be at least 0")
        Integer championshipPoints
) {
    public static ConstructorDto from(ConstructorEntity entity) {
        return new ConstructorDto(
                entity.getId(),
                entity.getName(),
                entity.getBaseLocation(),
                entity.getEngineSupplier(),
                entity.getTeamPrincipalUsername(),
                entity.getChampionshipPoints()
        );
    }
}