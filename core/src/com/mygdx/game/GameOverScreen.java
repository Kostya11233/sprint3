package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.managers.MyGdxGame;

public class GameOverScreen implements Screen {
    MyGdxGame game;
    int score;

    public GameOverScreen(MyGdxGame game, int score) {
        this.game = game;
        this.score = score;
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
        }
        ScreenUtils.clear(Color.BLACK);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "GAME OVER", GameSettings.SCREEN_WIDTH / 2f - 100, GameSettings.SCREEN_HEIGHT / 2f);
        game.font.draw(game.batch, "SCORE: " + score, GameSettings.SCREEN_WIDTH / 2f - 80, GameSettings.SCREEN_HEIGHT / 2f - 100);
        game.font.draw(game.batch, "TAP TO MENU", GameSettings.SCREEN_WIDTH / 2f - 100, GameSettings.SCREEN_HEIGHT / 2f - 200);
        game.batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}