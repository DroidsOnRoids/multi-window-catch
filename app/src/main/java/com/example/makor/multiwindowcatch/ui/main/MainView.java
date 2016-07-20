package com.example.makor.multiwindowcatch.ui.main;

import com.example.makor.multiwindowcatch.model.Pokemon;
import java.util.List;

public interface MainView {

    void showPokemons(List<Pokemon> pokemons);

    void showAppWithPokemonDetails(String pokemonId);

    class Empty implements MainView {

        @Override
        public void showPokemons(final List<Pokemon> pokemons) {

        }

        @Override
        public void showAppWithPokemonDetails(final String pokemonId) {

        }
    }
}
