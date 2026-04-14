package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.objects.*;
import java.util.ArrayList;

public class GameScreen implements Screen {
    MyGdxGame game;
    ShipObject ship;
    GameSession session;
    ArrayList<TrashObject> trash = new ArrayList<>();
    ArrayList<BulletObject> bullets = new ArrayList<>();
    MovingBackground bg;
    Buton btnMenu;

    public GameScreen(MyGdxGame game) {
        this.game = game;
        ship = new ShipObject(GameSettings.SCREEN_WIDTH / 2f, 150, game.world);
        session = new GameSession();
        bg = new MovingBackground(GameResources.BACKGROUND_IMG_PATH);
        btnMenu = new Buton(20, GameSettings.SCREEN_HEIGHT - 100, 100, 60, "MENU");
        game.world.setContactListener(new MyContactListener(session));
    }

    @Override
    public void show() { session.startGame(); }

    @Override
    public void render(float delta) {
        game.stepWorld();
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

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();

        bg.draw(game.batch);
        for (TrashObject t : trash) t.draw(game.batch);
        ship.draw(game.batch);
        for (BulletObject b : bullets) b.draw(game.batch);
        btnMenu.draw(game.batch);

        game.font.draw(game.batch, "SCORE: " + session.score, 50, GameSettings.SCREEN_HEIGHT - 50);
        game.font.draw(game.batch, "LIVES: " + session.lives, GameSettings.SCREEN_WIDTH - 150, GameSettings.SCREEN_HEIGHT - 50);

        game.batch.end();

        if (session.lives <= 0) {
            game.setScreen(new MenuScreen(game));
        }
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            game.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(game.touch);
            ship.move(game.touch);
            float tx = Gdx.input.getX();
            float ty = Gdx.input.getY();
            if (btnMenu.isHit(tx, ty)) {
                game.setScreen(new MenuScreen(game));
            }
        }
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        ship.dispose();
        bg.dispose();
        btnMenu.dispose();
        for (TrashObject t : trash) t.dispose();
        for (BulletObject b : bullets) b.dispose();
    }
}