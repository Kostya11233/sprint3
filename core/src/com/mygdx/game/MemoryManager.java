package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;

public class MemoryManager {
    private static final Preferences prefs = Gdx.app.getPreferences("User saves");

    public static void saveMusicSettings(boolean isOn) {
        prefs.putBoolean("isMusicOn", isOn);
        prefs.flush();
    }

    public static boolean loadIsMusicOn() {
        return prefs.getBoolean("isMusicOn", true);
    }


    public static void saveSoundSettings(boolean isOn) {
        prefs.putBoolean("isSoundOn", isOn);
        prefs.flush();
    }

    public static boolean loadIsSoundOn() {
        return prefs.getBoolean("isSoundOn", true);
    }


    public static void saveRecords(ArrayList<Integer> records) {
        Json json = new Json();
        prefs.putString("records", json.toJson(records));
        prefs.flush();
    }

    public static ArrayList<Integer> loadRecords() {
        if (!prefs.contains("records")) return new ArrayList<>();
        String jsonString = prefs.getString("records");
        return new Json().fromJson(ArrayList.class, jsonString);
    }
}