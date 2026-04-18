package com.goistech.core.services;

import com.goistech.core.models.PokemonData;

public interface PokemonApiService {
    /**
     * Fetches a Pokémon from PokeAPI by name or ID.
     * Returns null if the Pokémon is not found or the request fails.
     */
    PokemonData getPokemon(String nameOrId);
}
