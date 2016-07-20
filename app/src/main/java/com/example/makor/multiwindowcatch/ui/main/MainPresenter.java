package com.example.makor.multiwindowcatch.ui.main;

import com.example.makor.multiwindowcatch.model.Pokemon;
import com.example.makor.multiwindowcatch.utils.PokemonUrlCreator;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

public class MainPresenter {

    private MainView mMainView = new MainView.Empty();

    public void setView(final MainView mainView) {
        mMainView = mainView;
        showPokemons();
    }

    public void clearView() {
        mMainView = new MainView.Empty();
    }

    private void showPokemons() {
        final List<Pokemon> pokemons = getPokemonsFromDatabase();
        if (pokemons.isEmpty()) {
            createPokemonsAndShow();
        } else {
            mMainView.showPokemons(pokemons);
        }
    }

    private List<Pokemon> getPokemonsFromDatabase() {
        final List<Pokemon> pokemons = new ArrayList<>();

        final RealmResults<Pokemon> realmPokemons = Realm.getDefaultInstance().where(Pokemon.class).findAll();
        for (int i = realmPokemons.size() - 1; i >= 0; i--) {
            Pokemon pokemon = new Pokemon(realmPokemons.get(i).getId(), realmPokemons.get(i).getImageUrl());
            pokemons.add(pokemon);
        }

        return pokemons;
    }

    private void createPokemonsAndShow() {
        List<Pokemon> pokemons = new ArrayList<>();
        for (int i = 9; i >= 1; i--) {
            pokemons.add(new Pokemon(i, PokemonUrlCreator.createImageLinkUrl(i)));
        }
        showAndSavePokemons(pokemons);
    }

    private void showAndSavePokemons(final List<Pokemon> pokemons) {
        mMainView.showPokemons(pokemons);
        saveAsyncToDatabase(pokemons);
    }

    private void saveAsyncToDatabase(final List<Pokemon> pokemons) {
        final Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.executeTransactionAsync(realm -> executeAsyncSaveToDatabase(pokemons, realm));
    }

    private void executeAsyncSaveToDatabase(final List<Pokemon> pokemons, final Realm realm) {
        for (final Pokemon pokemon : pokemons) {
            realm.copyToRealm(pokemon);
        }
    }

    public void checkIfFromDeeplinkAndSave(final String pokemonImageUrl, final int pokemonId) {
        if (pokemonImageUrl != null && pokemonId != 0) {
            final Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            final Pokemon pokemon = realm.createObject(Pokemon.class);
            pokemon.setImageUrl(pokemonImageUrl);
            pokemon.setId(pokemonId);
            realm.commitTransaction();
        }
    }

    public void onPokemonClick(final String pokemonId) {
        mMainView.showAppWithPokemonDetails(pokemonId);
    }
}
