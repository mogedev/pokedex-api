package com.example.pokedexapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpecieDTO(
        @JsonProperty("evolution_chain")
        ResultDTO evolutionChain
) {}
