package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.components.MovingBackground;
public class SettingsScreen implements Screen {
    MyGdxGame game;
    MovingBackground bg;
    Buton btnReturn;
    Buton btnMusic;
    Buton btnSound;
    Buton btnClearRecords;

    boolean isMusicOn = true;
    boolean isSoundOn = true;

    public SettingsScreen(MyGdxGame game) {
        this.game = game;
        bg = new MovingBackground(GameResources.BACKGROUND_IMG_PATH);
        btnReturn = new Buton(260, 150, 200, 100, "RETURN");
        btnMusic = new Buton(260, 500, 200, 100, "MUSIC: ON");
        btnSound = new Buton(260, 350, 200, 100, "SOUND: ON");
        btnClearRecords = new Buton(260, 200, 200, 100, "CLEAR");
    }

    private void updateMusicButton() {
        btnMusic.text = "MUSIC: " + (isMusicOn ? "ON" : "OFF");
    }

    private void updateSoundButton() {
        btnSound.text = "SOUND: " + (isSoundOn ? "ON" : "OFF");
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            Vector3 touch = game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (btnReturn.isHit(touch.x, touch.y)) {
                game.setScreen(game.menuScreen);
            }
            if (btnMusic.isHit(touch.x, touch.y)) {
                isMusicOn = !isMusicOn;
                updateMusicButton();
                if (game.audioManager != null) {
                    game.audioManager.isMusicOn = isMusicOn;
                    game.audioManager.updateMusicFlag();
                }
            }
            if (btnSound.isHit(touch.x, touch.y)) {
                isSoundOn = !isSoundOn;
                updateSoundButton();
                if (game.audioManager != null) {
                    game.audioManager.isSoundOn = isSoundOn;
                }
            }
            if (btnClearRecords.isHit(touch.x, touch.y)) {
                System.out.println("Records cleared");
            }
        }

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