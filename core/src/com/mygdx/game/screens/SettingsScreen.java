package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Buton;
import com.mygdx.game.GameResources;
import com.mygdx.game.MemoryManager;
import com.mygdx.game.MenuScreen;
import com.mygdx.game.components.MovingBackground;
import com.mygdx.game.managers.MyGdxGame;
import java.util.ArrayList;

public class SettingsScreen implements Screen {
    MyGdxGame game;
    MovingBackground bg;
    Buton btnReturn;
    Buton btnMusic;
    Buton btnSound;
    Buton btnClearRecords;

    public SettingsScreen(MyGdxGame game) {
        this.game = game;
        bg = new MovingBackground(GameResources.BACKGROUND_IMG_PATH);
        btnReturn = new Buton(260, 150, 200, 100, "RETURN");
        btnMusic = new Buton(260, 550, 200, 100, "MUSIC: " + (game.audioManager.isMusicOn ? "ON" : "OFF"));
        btnSound = new Buton(260, 400, 200, 100, "SOUND: " + (game.audioManager.isSoundOn ? "ON" : "OFF"));
        btnClearRecords = new Buton(260, 250, 200, 100, "CLEAR");
    }

    @Override
    public void render(float delta) {
        handleInput();

        ScreenUtils.clear(Color.BLACK);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

        bg.draw(game.batch);
        game.font.draw(game.batch, "SETTINGS", 300, 900);
        btnMusic.draw(game.batch);
        btnSound.draw(game.batch);
        btnClearRecords.draw(game.batch);
        btnReturn.draw(game.batch);

        game.batch.end();
    }

    private void handleInput() {
        if (!Gdx.input.isTouched()) return;


        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();


        Vector3 touch = new Vector3(screenX, screenY, 0);
        game.camera.unproject(touch);

        float worldX = touch.x;
        float worldY = touch.y;


        if (btnReturn.isHit(worldX, worldY)) {
            game.setScreen(new MenuScreen(game));
            return;
        }
        if (btnMusic.isHit(worldX, worldY)) {
            boolean newState = !game.audioManager.isMusicOn;
            MemoryManager.saveMusicSettings(newState);
            game.audioManager.isMusicOn = newState;
            game.audioManager.updateMusicFlag();
            btnMusic.setText("MUSIC: " + (newState ? "ON" : "OFF"));
            return;
        }
        if (btnSound.isHit(worldX, worldY)) {
            boolean newState = !game.audioManager.isSoundOn;
            MemoryManager.saveSoundSettings(newState);
            game.audioManager.isSoundOn = newState;
            btnSound.setText("SOUND: " + (newState ? "ON" : "OFF"));
            return;
        }
        if (btnClearRecords.isHit(worldX, worldY)) {
            ArrayList<Integer> emptyList = new ArrayList<Integer>();
            MemoryManager.saveRecords(emptyList);
            return;
        }
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override public void dispose() {
        bg.dispose();
        btnReturn.dispose();
        btnMusic.dispose();
        btnSound.dispose();
        btnClearRecords.dispose();
    }
}