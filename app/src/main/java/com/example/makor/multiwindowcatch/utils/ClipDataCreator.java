package com.example.makor.multiwindowcatch.utils;

import android.content.ClipData;

public class ClipDataCreator {

    private static final String POKEBALL = "POKEBALL";

    public static ClipData getClipData() {
        ClipData.Item item = new ClipData.Item(POKEBALL);
        return new ClipData(POKEBALL, new String[]{}, item);
    }
}
