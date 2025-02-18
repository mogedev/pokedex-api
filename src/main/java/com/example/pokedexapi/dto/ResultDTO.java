package com.example.pokedexapi.dto;

import java.io.Serializable;

public record ResultDTO(
        String name,
        String url
) implements Serializable {
}
