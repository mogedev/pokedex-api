package com.example.pokedexapi.controller;

import com.example.pokedexapi.dto.PokemonDTO;
import com.example.pokedexapi.service.PokedexService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PokedexController.class)
public class PokedexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PokedexService pokedexService;

    @Test
    void getAllPokemons() throws Exception {
        PokemonDTO pokemonDTO = new PokemonDTO(1, "bulbasaur", "grass", 69, List.of("overgrow", "chlorophyll"), "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png", null, null);

        given(pokedexService.findAllPokemon(PageRequest.of(0, 10))).willReturn(new PageImpl<>(List.of(pokemonDTO)));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/")
                    .queryParam("page", "0")
                    .queryParam("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").isNotEmpty());
    }


    @Test
    void getPokemonById() throws Exception {
        PokemonDTO pokemonDTO = new PokemonDTO(
                1, "bulbasaur","grass", 69,
                List.of("overgrow", "chlorophyll"),"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                "Loves to eat",
                List.of("bulbasaur", "ivysaur", "venusaur"));

        given(pokedexService.findPokemonById(1)).willReturn(pokemonDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(pokemonDTO.name()));
    }

    @Test
    void givenPokemon_whenFindById_thenReturnBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detail").exists());
    }
}
