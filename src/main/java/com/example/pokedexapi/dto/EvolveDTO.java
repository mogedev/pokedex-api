package com.example.pokedexapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record EvolveDTO(
        @JsonProperty("evolves_to")
        List<EvolveDTO> evolveTo,
        ResultDTO species
) {
}
