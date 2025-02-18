package com.example.pokedexapi.controller;

import com.example.pokedexapi.dto.PokemonDTO;
import com.example.pokedexapi.service.PokedexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Pokedex Api",
        description = "This API provides a set of endpoints to retrieve information about Pokemon from the comprehensive Pokedex API. It serves as a convenient way to access and utilize Pokemon data for various purposes, such as building Pokedex applications, creating Pokemon-themed games, or conducting research on Pokemon characteristics."
)
@Validated
@RestController
public class PokedexController {

    private final PokedexService pokedexService;

    public PokedexController(PokedexService pokedexService) {
        this.pokedexService = pokedexService;
    }

    @Operation(summary = "Get pokemon pagination")
    @ApiResponse(responseCode = "200", description = "Pokemon page")
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<PokemonDTO>> findPokemons(
            @Parameter(name = "size", description = "Page size", example = "10", required = true)
            @Min(value = 1, message = "Page size must be greater than zero")
            @RequestParam("size") Integer size,
            @Parameter(name = "page", description = "Page number", example = "0", required = true)
            @Min(value = 0, message = "Page number must be greater than zero")
            @RequestParam("page") Integer page
    ) {
        return ResponseEntity.ok(pokedexService.findAllPokemon(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PokemonDTO> findPokemonById(
            @Parameter(name = "id", description = "Pokemon id", example = "1", required = true)
            @Min(value = 1, message = "Pokemon ID must be greater than zero")
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(pokedexService.findPokemonById(id));

    }

}
