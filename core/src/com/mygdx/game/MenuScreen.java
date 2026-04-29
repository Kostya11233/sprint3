package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.managers.MyGdxGame;

import java.util.ArrayList;

public class MenuScreen implements Screen {
    MyGdxGame game;
    Buton btnStart, btnMusic, btnExit;
    ArrayList<Integer> records;

    public MenuScreen(MyGdxGame game) {
        this.game = game;
        btnStart = new Buton(260, 550, 200, 100, "START");
        btnMusic = new Buton(260, 400, 200, 100, "MUSIC: " + (game.audioManager.isMusicOn ? "ON" : "OFF"));
        btnExit  = new Buton(260, 250, 200, 100, "EXIT");
        records = MemoryManager.loadRecords();
    }

    @Override
    public void render(float delta) {
        handleInput();

        ScreenUtils.clear(Color.BLACK);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

        game.font.draw(game.batch, "SPACE CLEANER", 260, 700);
        btnStart.draw(game.batch);
        btnMusic.draw(game.batch);
        btnExit.draw(game.batch);


        float y = GameSettings.SCREEN_HEIGHT - 50;
        game.font.draw(game.batch, "--- TOP 7 RECORDS ---", 250, y);
        y -= 40;
        for (int i = 0; i < Math.min(records.size(), 7); i++) {
            game.font.draw(game.batch, (i + 1) + ". " + records.get(i), 300, y);
            y -= 35;
        }

        game.batch.end();
    }

    private void handleInput() {
        if (!Gdx.input.isTouched()) return;

        Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.camera.unproject(touch);

        float worldX = touch.x;
        float worldY = touch.y;

        if (btnStart.isHit(touch.x, touch.y)) {
            game.setScreen(new GameScreen(game));
            return;
        }
        if (btnMusic.isHit(touch.x, touch.y)) {
            game.audioManager.toggleMusic();
            btnMusic.setText("MUSIC: " + (game.audioManager.isMusicOn ? "ON" : "OFF"));
            return;
        }
        if (btnExit.isHit(touch.x, touch.y)) {
            Gdx.app.exit();
            return;
        }
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override public void dispose() {
        btnStart.dispose();
        btnMusic.dispose();
        btnExit.dispose();
    }
}