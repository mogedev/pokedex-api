package com.example.pokedexapi.service;

import com.example.pokedexapi.client.PokedexClient;
import com.example.pokedexapi.dto.*;
import com.example.pokedexapi.mapper.PokemonDTOMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PokedexServiceTest {

    @InjectMocks
    private PokedexServiceImpl pokedexService;

    @Mock
    private PokedexClient pokedexClient;

    @Mock
    private PokemonDTOMapper pokemonDTOMapper;

    @BeforeEach
    public void setup() {
        ResultDTO resultDTO = new ResultDTO("charmander", null);
        PokedexDTO pokedexDTO = new PokedexDTO(100, "","", List.of(resultDTO));

        TypesDTO typesDTO = new TypesDTO(new ResultDTO("fire", ""));
        AbilitiesDTO abilitiesDTO = new AbilitiesDTO(new ResultDTO("blaze", ""));
        SpriteDTO spriteDTO = new SpriteDTO(new OtherSpriteDTO(new OfficialArtworkDTO("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png")));
        PokemonInfoDTO pokemonInfoDTO = new PokemonInfoDTO(4, "charmander", List.of(typesDTO), 69, List.of(abilitiesDTO), spriteDTO);

        SpecieDTO specieDTO = new SpecieDTO(new ResultDTO(null, "https://pokeapi.co/api/v2/evolution-chain/2/"));
        CharacteristicDTO characteristicDTO = new CharacteristicDTO(List.of(new DescriptionDTO("Highly curious", new ResultDTO("en", null))));
        EvolutionDTO evolutionDTO = new EvolutionDTO(new EvolveDTO( List.of(new EvolveDTO(List.of(new EvolveDTO(new ArrayList<>(), new ResultDTO("charizard", null))), new ResultDTO("charmeleon", null))), new ResultDTO("charmander", null)));

        PokemonDTO pokemonDTO = new PokemonDTO(4, "charmander", "fire", 69,  List.of("blaze"), "", "Highly curious", List.of("charmander", "charmaleon", "charizard"));

        Mockito.lenient().when(pokedexClient.pokedex(10, 0)).thenReturn(pokedexDTO);
        Mockito.when(pokedexClient.pokemonByName("charmander")).thenReturn(pokemonInfoDTO);
        Mockito.when(pokemonDTOMapper.pokemonInfoToPokemon(pokemonInfoDTO, null, null)).thenReturn(pokemonDTO);

        Mockito.when(pokedexClient.pokemonById(4)).thenReturn(pokemonInfoDTO);
        Mockito.when(pokedexClient.pokemonSpecies(4)).thenReturn(specieDTO);
        Mockito.when(pokedexClient.characteristic(4)).thenReturn(characteristicDTO);
        Mockito.when(pokedexClient.evolutionChain(2)).thenReturn(evolutionDTO);
        Mockito.when(pokemonDTOMapper.pokemonInfoToPokemon(pokemonInfoDTO, characteristicDTO, evolutionDTO)).thenReturn(pokemonDTO);
    }

    @Test
    void givenPokedexService_whenGetPokedex_thenReturnPokedexDTO() {
        Page<PokemonDTO> page = pokedexService.findAllPokemon(PageRequest.of(0, 10));

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getTotalElements()).isEqualTo(100);
        assertThat(page.getContent().get(0).name()).isEqualTo("charmander");
    }

    @Test
    void givenPokedexService_whenGetPokemonById_thenReturnPokemonDTO() {
        PokemonDTO pokemonDTO = pokedexService.findPokemonById(4);

        log.info("{}",pokemonDTO);
        assertThat(pokemonDTO.name()).isEqualTo("charmander");
    }
}
