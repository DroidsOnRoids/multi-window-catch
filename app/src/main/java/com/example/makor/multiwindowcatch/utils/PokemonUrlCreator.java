package com.example.makor.multiwindowcatch.utils;

public class PokemonUrlCreator {

    public static String createImageLinkUrl(final int pokemonId) {
        return "http://pokeapi.co/media/sprites/pokemon/" + pokemonId + ".png";
    }
}
