package com.example.pokedexapi.service;

import com.example.pokedexapi.client.PokedexClient;
import com.example.pokedexapi.dto.*;
import com.example.pokedexapi.mapper.PokemonDTOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PokedexServiceImpl implements PokedexService {

    private final PokedexClient pokedexClient;
    private final PokemonDTOMapper pokemonDTOMapper;

    PokedexServiceImpl(PokedexClient pokedexClient, PokemonDTOMapper pokemonDTOMapper) {
        this.pokedexClient = pokedexClient;
        this.pokemonDTOMapper = pokemonDTOMapper;
    }


    @Cacheable(cacheNames = "findAllPokemon")
    @Override
    public Page<PokemonDTO> findAllPokemon(Pageable pageable) {
        log.info("Getting pokemon list");
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        PokedexDTO pokedexDTO = pokedexClient.pokedex(size,page);
        List<PokemonDTO> pokemonDTOList =  pokedexDTO.results().stream().map(pokemon -> pokemonDTOMapper.pokemonInfoToPokemon(pokedexClient.pokemonByName(pokemon.name()), null, null)).toList();

        return new PageImpl<>(pokemonDTOList, PageRequest.of(page, size), pokedexDTO.count());
    }

    @Cacheable("findPokemonById")
    @Override
    public PokemonDTO findPokemonById(Integer id) {
        log.info("Getting pokemon by id {}", id);

        PokemonInfoDTO pokemonInfo = pokedexClient.pokemonById(id);
        log.info("Found pokemon {}", pokemonInfo);
        if (pokemonInfo == null) {
            return null;
        }

        SpecieDTO specieDTO = pokedexClient.pokemonSpecies(pokemonInfo.id());
        String[] url = specieDTO.evolutionChain().url().split("/");
        CharacteristicDTO characteristic = pokedexClient.characteristic(pokemonInfo.id());
        EvolutionDTO evolutions = pokedexClient.evolutionChain(Integer.parseInt(url[url.length - 1]));

        return pokemonDTOMapper.pokemonInfoToPokemon(pokemonInfo, characteristic, evolutions);
    }
}
