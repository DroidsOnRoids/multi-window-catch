package com.example.makor.multiwindowcatch.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.makor.multiwindowcatch.R;
import com.example.makor.multiwindowcatch.model.Pokemon;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final Context mContext;
    private List<Pokemon> mPokemons;
    private OnClickListener mOnClickListener;

    public Adapter(final Context context) {
        mContext = context;
        mPokemons = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Pokemon pokemon = mPokemons.get(position);

        Picasso.with(mContext).load(pokemon.getImageUrl()).fit().centerCrop().into(holder.mImagePokemon);
        holder.mImagePokemon.setOnClickListener(view -> onPokemonClick(String.valueOf(pokemon.getId())));
    }

    private void onPokemonClick(final String pokemonId) {
        if (mOnClickListener != null) mOnClickListener.onPokemonClick(pokemonId);
    }

    @Override
    public int getItemCount() {
        return mPokemons.size();
    }

    public void setOnClickListener(final OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setPokemons(final List<Pokemon> pokemons) {
        mPokemons = pokemons;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_pokemon) ImageView mImagePokemon;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickListener {
        void onPokemonClick(final String pokemonId);
    }
}
