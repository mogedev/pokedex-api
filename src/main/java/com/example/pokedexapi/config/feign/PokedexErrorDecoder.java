package com.example.pokedexapi.config.feign;

import com.example.pokedexapi.config.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class PokedexErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        if (response.status() == 404) {
            return new NotFoundException(response.reason());
        }
        return defaultErrorDecoder.decode(s, response);
    }
}
