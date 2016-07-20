package com.example.makor.multiwindowcatch.ui.pokeball;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import com.example.makor.multiwindowcatch.R;
import com.example.makor.multiwindowcatch.utils.ClipDataCreator;

public class PokeballActivity extends AppCompatActivity {

    @BindView(R.id.image_pokeball) ImageView mImagePokeball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokeball);
        ButterKnife.bind(this);

        setupUpHomeBack();
    }

    private void setupUpHomeBack() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnLongClick(R.id.image_pokeball)
    public boolean onPokeballLongClick() {
        if (isAndroidNOrLater()) {
            startPokeballDragAndDrop();
        }
        return true;
    }

    private boolean isAndroidNOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    @SuppressLint("NewApi")
    private void startPokeballDragAndDrop() {
        mImagePokeball.startDragAndDrop(
                ClipDataCreator.getClipData(),
                new View.DragShadowBuilder(mImagePokeball),
                null,
                View.DRAG_FLAG_GLOBAL);
    }
}
