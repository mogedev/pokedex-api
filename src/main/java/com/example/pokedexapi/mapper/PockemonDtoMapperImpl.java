package com.example.pokedexapi.mapper;

import com.example.pokedexapi.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PockemonDtoMapperImpl implements PokemonDTOMapper {

    @Override
    public PokemonDTO pokemonInfoToPokemon(PokemonInfoDTO pokemonInfoDTO, CharacteristicDTO characteristic, EvolutionDTO evolutions) {

        AtomicReference<String> type = new AtomicReference<>("");
        pokemonInfoDTO.types().stream().findFirst().ifPresent(typesDTO -> type.set(typesDTO.type().name()));

        String image = pokemonInfoDTO.sprites() == null ? "" :
                    pokemonInfoDTO.sprites().other() == null ? "" :
                            pokemonInfoDTO.sprites().other().officialArtwork() == null ? "" :
                                    pokemonInfoDTO.sprites().other().officialArtwork().frontDefault() == null ? "" :
                                            pokemonInfoDTO.sprites().other().officialArtwork().frontDefault();

        List<String> abilities  = new ArrayList<>();
        pokemonInfoDTO.abilities().forEach(abilitiesDTO -> abilities.add(abilitiesDTO.ability().name()));

        String description = null;
        if (characteristic != null) {
            AtomicReference<String> desc = new AtomicReference<>("");
            characteristic.descriptions().stream().filter(descriptionDTO -> descriptionDTO.language().name().equals("en")).findFirst().ifPresent(descriptionDTO -> desc.set(descriptionDTO.description()));
            description = desc.get();
        }
        List<String> evolutionList = null;
        if (evolutions != null) {
            evolutionList = new ArrayList<>();
            evolutionList.add(evolutions.chain().species().name());
            evolutionList.addAll(evolvesTo(evolutions.chain().evolveTo()));
        }
        return new PokemonDTO(
                pokemonInfoDTO.id(),
                pokemonInfoDTO.name(),
                type.get(),
                pokemonInfoDTO.weight(),
                abilities,
                image,
                description,
                evolutionList
        );
    }

    private List<String> evolvesTo(List<EvolveDTO> evolveDTOList) {
        if (evolveDTOList.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> evolveList = new ArrayList<>();
        evolveList.add(evolveDTOList.get(0).species().name());
        evolveList.addAll(evolvesTo(evolveDTOList.get(0).evolveTo()));
        return evolveList;
    }

}