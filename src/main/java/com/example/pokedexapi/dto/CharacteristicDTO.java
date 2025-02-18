package com.example.pokedexapi.dto;

import java.util.List;

public record CharacteristicDTO(
        List<DescriptionDTO> descriptions
) {
}
