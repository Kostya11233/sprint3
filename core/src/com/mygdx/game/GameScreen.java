package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.objects.ShipObject;

public class GameScreen implements Screen {

    MyGdxGame myGdxGame;
    ShipObject shipObject;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2f, 150,
                GameSettings.SHIP_WIDTH,
                GameSettings.SHIP_HEIGHT,
                myGdxGame.world
        );
    }

    @Override
    public void render(float delta) {
        myGdxGame.stepWorld();
        handleInput();
        draw();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.camera.unproject(myGdxGame.touch);
            shipObject.move(myGdxGame.touch);
        }
    }

    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        shipObject.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    @Override public void show() {

    }
    @Override public void resize(int width, int height) {


    }
    @Override public void pause() {

    }
    @Override public void resume() {

    }
    @Override public void hide() {

    }
    @Override public void dispose(

    ) {
        shipObject.dispose();
    }
}