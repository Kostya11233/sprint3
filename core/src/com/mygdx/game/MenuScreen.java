package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {
    MyGdxGame game;
    Buton btnStart;
    Buton btnExit;
    public MenuScreen(MyGdxGame game) {
        this.game = game;
        btnExit = new Buton(GameSettings.SCREEN_WIDTH / 2f - 100, 200, 200, 100, "EXIT");
        btnStart = new Buton(GameSettings.SCREEN_WIDTH / 2f - 100, 400, 200, 100, "START");

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {


            Vector3 touch = game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (btnStart.isHit(touch.x, touch.y)) {
                game.setScreen(new GameScreen(game));
                return;
            }
            if (btnExit.isHit(touch.x, touch.y)) {
                Gdx.app.exit();
                return;
            }
        }


        ScreenUtils.clear(Color.BLACK);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "SPACE CLEANER", 260, 700);
        btnStart.draw(game.batch);
        btnExit.draw(game.batch);
        game.batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        btnStart.dispose();
        btnExit.dispose();
    }
}