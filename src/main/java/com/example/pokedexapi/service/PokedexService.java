package com.example.pokedexapi.service;

import com.example.pokedexapi.dto.PokemonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PokedexService {

    Page<PokemonDTO> findAllPokemon(Pageable pageable);

    PokemonDTO findPokemonById(Integer id);

}
