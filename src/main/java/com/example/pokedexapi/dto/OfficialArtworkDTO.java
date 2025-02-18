package com.example.pokedexapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OfficialArtworkDTO(
        @JsonProperty("front_default")
        String frontDefault
) {
}
