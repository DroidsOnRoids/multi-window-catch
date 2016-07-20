package com.example.makor.multiwindowcatch.model;

import io.realm.RealmObject;

public class Pokemon extends RealmObject {

    private int id;
    private String imageUrl;

    public Pokemon(){}

    public Pokemon(final int id, final String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
