package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.components.MovingBackground;
import com.mygdx.game.objects.*;
import com.mygdx.game.managers.MyContactListener;
import java.util.ArrayList;

public class GameScreen implements Screen {
    MyGdxGame game;
    ShipObject ship;
    GameSession session;
    ArrayList<TrashObject> trash = new ArrayList<>();
    ArrayList<BulletObject> bullets = new ArrayList<>();
    MovingBackground bg;
    Texture menuTexture;
    float menuX, menuY, menuW, menuH;
    Texture lifeTexture;

    public GameScreen(MyGdxGame game) {
        this.game = game;
        ship = new ShipObject(GameSettings.SCREEN_WIDTH / 2f, 150, game.world);
        session = new GameSession();
        bg = new MovingBackground(GameResources.BACKGROUND_IMG_PATH);
        menuTexture = new Texture(GameResources.PAUSE_ICON_PATH);
        menuW = 60;
        menuH = 60;
        menuX = 20;
        menuY = GameSettings.SCREEN_HEIGHT - menuH - 20;
        game.world.setContactListener(new MyContactListener(session));
        lifeTexture = new Texture(GameResources.LIFE_ICON_PATH);
        session.startGame();
    }

    @Override
    public void render(float delta) {
        handleInput();

        if (session.needToShoot()) {
            bullets.add(new BulletObject(ship.getX(), ship.getY() + ship.getHeight() / 2, game.world));
        }
        if (session.shouldSpawnTrash()) {
            trash.add(new TrashObject(game.world));
        }

        for (int i = 0; i < trash.size(); i++) {
            TrashObject t = trash.get(i);
            if (t.wasHit || !t.isInFrame()) {
                game.world.destroyBody(t.getBody());
                trash.remove(i);
                i--;
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            BulletObject b = bullets.get(i);
            if (b.wasHit || b.hasToBeDestroyed()) {
                game.world.destroyBody(b.getBody());
                bullets.remove(i);
                i--;
            }
        }

        bg.move();
        game.stepWorld();

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();

        bg.draw(game.batch);
        for (TrashObject t : trash) t.draw(game.batch);
        ship.draw(game.batch);
        for (BulletObject b : bullets) b.draw(game.batch);
        game.batch.draw(menuTexture, menuX, menuY, menuW, menuH);

        game.font.draw(game.batch, "SCORE: " + session.score, 50, GameSettings.SCREEN_HEIGHT - 50);
        for (int i = 0; i < session.lives; i++) {
            game.batch.draw(lifeTexture, GameSettings.SCREEN_WIDTH - 50 - i * 55, GameSettings.SCREEN_HEIGHT - 70, 45, 45);
        }

        game.batch.end();

        if (session.lives <= 0) {
            game.setScreen(new MenuScreen(game));
        }
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            float tx = Gdx.input.getX();
            float ty = Gdx.input.getY();
            float realY = GameSettings.SCREEN_HEIGHT - ty;


            if (tx >= menuX && tx <= menuX + menuW && realY >= menuY && realY <= menuY + menuH) {
                game.setScreen(new MenuScreen(game));
                return;
            }


            game.touch.set(tx, ty, 0);
            game.camera.unproject(game.touch);
            ship.move(game.touch);
        }
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        ship.dispose();
        bg.dispose();
        menuTexture.dispose();
        lifeTexture.dispose();
        for (TrashObject t : trash) t.dispose();
        for (BulletObject b : bullets) b.dispose();
    }
}