package com.example.pokedexapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OtherSpriteDTO(
        @JsonProperty("official-artwork")
        OfficialArtworkDTO officialArtwork
) {
}
