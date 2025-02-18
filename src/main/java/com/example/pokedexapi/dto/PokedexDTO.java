package com.example.pokedexapi.dto;

import java.io.Serializable;
import java.util.List;

public record PokedexDTO (
        Integer count,
        String next,
        String previous,
        List<ResultDTO> results
) implements Serializable {
}
