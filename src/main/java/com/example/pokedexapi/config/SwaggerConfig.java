package com.example.pokedexapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
        title = "Pokedex API",
        description = "This API provides a set of endpoints to retrieve information about Pokemon from the comprehensive Pokedex API. It serves as a convenient way to access and utilize Pokemon data for various purposes, such as building Pokedex applications, creating Pokemon-themed games, or conducting research on Pokemon characteristics.",
        version = "1.0"
    )
)
public class SwaggerConfig {
}
