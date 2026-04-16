package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.components.MovingBackground;

public class MenuScreen implements Screen {
    MyGdxGame game;
    Buton btnStart;
    Buton btnSettings;
    Buton btnExit;
    MovingBackground bg;

    public MenuScreen(MyGdxGame game) {
        this.game = game;
        bg = new MovingBackground(GameResources.BACKGROUND_IMG_PATH);
        btnStart = new Buton(GameSettings.SCREEN_WIDTH / 2f - 100, 550, 200, 100, "START");
        btnSettings = new Buton(GameSettings.SCREEN_WIDTH / 2f - 100, 400, 200, 100, "SETTINGS");
        btnExit = new Buton(GameSettings.SCREEN_WIDTH / 2f - 100, 250, 200, 100, "EXIT");
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            Vector3 touch = game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (btnStart.isHit(touch.x, touch.y)) {
                game.setScreen(game.gameScreen);
            }
            if (btnSettings.isHit(touch.x, touch.y)) {
                game.setScreen(game.settingsScreen);
            }
            if (btnExit.isHit(touch.x, touch.y)) {
                Gdx.app.exit();
            }
        }

        ScreenUtils.clear(Color.BLACK);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

        bg.draw(game.batch);
        game.font.draw(game.batch, "SPACE CLEANER", 260, 900);
        btnStart.draw(game.batch);
        btnSettings.draw(game.batch);
        btnExit.draw(game.batch);

        game.batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        bg.dispose();
        btnStart.dispose();
        btnSettings.dispose();
        btnExit.dispose();
    }
}