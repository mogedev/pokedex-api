package com.example.pokedexapi.dto;

import java.util.List;

public record PokemonInfoDTO(
        Integer id,
        String name,
        List<TypesDTO> types,
        Integer weight,
        List<AbilitiesDTO> abilities,
        SpriteDTO sprites
) {
}
