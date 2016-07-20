package com.example.makor.multiwindowcatch;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PokemonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
