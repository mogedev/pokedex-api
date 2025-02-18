package com.example.pokedexapi.client;

import com.example.pokedexapi.dto.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pokedexapi")
public interface PokedexClient {

    @GetMapping("/pokemon/?limit={size}&offset={offset}")
    PokedexDTO pokedex(@RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset);

    @GetMapping("/pokemon/{pokemonName}")
    PokemonInfoDTO pokemonByName(@PathVariable String pokemonName);

    @GetMapping("/pokemon/{pokemonId}")
    PokemonInfoDTO pokemonById(@PathVariable Integer pokemonId);

    @GetMapping("/pokemon-species/{pokemonId}")
    SpecieDTO pokemonSpecies(@PathVariable Integer pokemonId);

    @GetMapping("/evolution-chain/{id}")
    EvolutionDTO evolutionChain(@PathVariable Integer id);

    @GetMapping("/characteristic/{id}")
    CharacteristicDTO characteristic(@PathVariable Integer id);
}
