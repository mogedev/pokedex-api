package com.example.pokedexapi.mapper;

import com.example.pokedexapi.dto.CharacteristicDTO;
import com.example.pokedexapi.dto.EvolutionDTO;
import com.example.pokedexapi.dto.PokemonDTO;
import com.example.pokedexapi.dto.PokemonInfoDTO;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface PokemonDTOMapper {
    @Mapping(target = "abilities", source = "pokemonInfoDTO.abilities", ignore = true)
    @Mapping(target = "description", source = "characteristic", ignore = true)
    @Mapping(target = "evolutions", source = "evolutions", ignore = true)
    PokemonDTO pokemonInfoToPokemon(PokemonInfoDTO pokemonInfoDTO, @Nullable CharacteristicDTO characteristic, @Nullable EvolutionDTO evolutions);
}
