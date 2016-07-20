package com.example.makor.multiwindowcatch.ui.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.makor.multiwindowcatch.R;
import com.example.makor.multiwindowcatch.model.Pokemon;
import com.example.makor.multiwindowcatch.model.PokemonConstants;
import com.example.makor.multiwindowcatch.ui.pokeball.PokeballActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, Adapter.OnClickListener {

    @BindView(R.id.layout_container) FrameLayout mLayoutContainer;
    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;

    private MainPresenter mPresenter;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new MainPresenter();

        setupAdapter();
        setupRecyclerView(getRecyclerViewOrientation(getResources().getConfiguration().orientation));

        checkIfFromDeeplinkAndSave();
    }

    private void setupAdapter() {
        mAdapter = new Adapter(this);
        mAdapter.setOnClickListener(this);
    }

    private void setupRecyclerView(final int orientation) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, orientation, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void checkIfFromDeeplinkAndSave() {
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mPresenter.checkIfFromDeeplinkAndSave(
                    extras.getString(PokemonConstants.POKEBALL_IMAGE_URL),
                    extras.getInt(PokemonConstants.POKEMON_ID));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.setView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.clearView();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pokeball:
                startActivity(new Intent(this, PokeballActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setupRecyclerView(getRecyclerViewOrientation(newConfig.orientation));
    }

    private int getRecyclerViewOrientation(final int orientation) {
        return isOrientationPortrait(orientation) ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL;
    }

    private boolean isOrientationPortrait(final int orientation) {
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public void onMultiWindowModeChanged(final boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        mLayoutContainer.setBackgroundColor(isInMultiWindowMode ? Color.LTGRAY : Color.TRANSPARENT);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showPokemons(final List<Pokemon> pokemons) {
        mAdapter.setPokemons(pokemons);
    }

    @Override
    public void onPokemonClick(final String pokemonId) {
        mPresenter.onPokemonClick(pokemonId);
    }

    @Override
    public void showAppWithPokemonDetails(final String pokemonId) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.makor.multiwindowsdropadjacent");
        if (intent == null) {
            return;
        }
        intent.putExtra(PokemonConstants.POKEMON_ID, pokemonId);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
