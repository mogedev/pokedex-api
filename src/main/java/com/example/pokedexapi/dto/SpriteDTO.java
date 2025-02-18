package com.example.pokedexapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpriteDTO(
        OtherSpriteDTO other
) {
}
