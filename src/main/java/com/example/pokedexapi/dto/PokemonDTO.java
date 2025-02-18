package com.example.pokedexapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PokemonDTO(
        Integer id,
        String name,
        String type,
        Integer weight,
        List<String> abilities,
        String image,
        String description,
        List<String> evolutions
) implements Serializable {
}
