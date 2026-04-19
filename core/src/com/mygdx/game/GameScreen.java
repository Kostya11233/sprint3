package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.components.MovingBackground;
import com.mygdx.game.managers.MyGdxGame;
import com.mygdx.game.objects.*;
import com.mygdx.game.managers.MyContactListener;
import java.util.ArrayList;


public class GameScreen implements Screen {
    MyGdxGame game;
    ShipObject ship;
    GameSession session;
    ArrayList<TrashObject> trash = new ArrayList<>();
    ArrayList<BulletObject> bullets = new ArrayList<>();
    ArrayList<HealthPackObject> healthPacks = new ArrayList<>();
    MovingBackground bg;
    Texture menuTexture;
    float menuX, menuY, menuW, menuH;
    Texture lifeTexture;
    boolean isPaused = false;
    Buton btnResume, btnMusicToggle, btnExitToMenu;

    public GameScreen(MyGdxGame game) {
        this.game = game;
        ship = new ShipObject(GameSettings.SCREEN_WIDTH / 2f, 150, game.world);
        session = new GameSession();
        bg = new MovingBackground(GameResources.BACKGROUND_IMG_PATH);
        game.world.setContactListener(new MyContactListener(session, game.audioManager));
        lifeTexture = new Texture(GameResources.LIFE_ICON_PATH);

        menuTexture = new Texture(GameResources.PAUSE_ICON_PATH);
        menuW = 60; menuH = 60;
        menuX = 20;
        menuY = GameSettings.SCREEN_HEIGHT - menuH - 20;

        btnResume = new Buton(260, 500, 200, 100, "RESUME");
        btnMusicToggle = new Buton(260, 350, 200, 100, "MUSIC: " + (game.audioManager.isMusicOn ? "ON" : "OFF"));
        btnExitToMenu = new Buton(260, 200, 200, 100, "EXIT");

        session.startGame();
    }

    @Override
    public void render(float delta) {
        handleInput();

        if (!isPaused && session.isGameStarted() && session.lives > 0) {

            if (session.shouldSpawnTrash()) trash.add(new TrashObject(game.world));
            if (session.shouldSpawnHealthPack()) healthPacks.add(new HealthPackObject(game.world));
            if (session.needToShoot()) {
                bullets.add(new BulletObject(ship.getX(), ship.getY() + ship.getHeight() / 2, game.world));
                game.audioManager.shootSound.play();
            }



            for (int i = 0; i < trash.size(); i++) {
                TrashObject t = trash.get(i);
                if (t.wasHit || !t.isInFrame()) {
                    if (t.getBody() != null) game.world.destroyBody(t.getBody());
                    trash.remove(i);
                    i--;
                }
            }


            for (int i = 0; i < bullets.size(); i++) {
                BulletObject b = bullets.get(i);
                if (b.wasHit || b.hasToBeDestroyed()) {
                    if (b.getBody() != null) game.world.destroyBody(b.getBody());
                    bullets.remove(i);
                    i--;
                }
            }


            for (int i = 0; i < healthPacks.size(); i++) {
                HealthPackObject h = healthPacks.get(i);
                if (h.isCollected || !h.isInFrame()) {
                    if (h.getBody() != null) game.world.destroyBody(h.getBody());
                    healthPacks.remove(i);
                    i--;
                }
            }

            bg.move();
            game.stepWorld();
        }


        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();

        bg.draw(game.batch);
        for (TrashObject t : trash) t.draw(game.batch);
        for (HealthPackObject h : healthPacks) h.draw(game.batch);
        ship.draw(game.batch);
        for (BulletObject b : bullets) b.draw(game.batch);
        game.batch.draw(menuTexture, menuX, menuY, menuW, menuH);

        game.font.draw(game.batch, "SCORE: " + session.getScore(), 50, GameSettings.SCREEN_HEIGHT - 50);
        for (int i = 0; i < session.lives; i++)
            game.batch.draw(lifeTexture, GameSettings.SCREEN_WIDTH - 50 - i * 55, GameSettings.SCREEN_HEIGHT - 70, 45, 45);

        if (isPaused) {
            btnResume.draw(game.batch);
            btnMusicToggle.draw(game.batch);
            btnExitToMenu.draw(game.batch);
        }

        game.batch.end();

        if (session.lives <= 0) {
            session.endGame();
            game.setScreen(new MenuScreen(game));
        }
    }

    private void handleInput() {
        if (!Gdx.input.isTouched()) return;

        Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.camera.unproject(touch);

        float worldX = touch.x;
        float worldY = touch.y;


        if (!isPaused && worldX >= menuX && worldX <= menuX + menuW &&
                worldY >= menuY && worldY <= menuY + menuH) {
            isPaused = true;
            return;
        }


        if (isPaused) {
            if (btnResume.isHit(worldX, worldY)) {
                isPaused = false;
                return;
            }
            if (btnMusicToggle.isHit(worldX, worldY)) {
                game.audioManager.toggleMusic();
                btnMusicToggle.setText("MUSIC: " + (game.audioManager.isMusicOn ? "ON" : "OFF"));
                return;
            }
            if (btnExitToMenu.isHit(worldX, worldY)) {
                game.setScreen(new MenuScreen(game));
                return;
            }
            return;
        }


        ship.move(touch);
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
        btnResume.dispose();
        btnMusicToggle.dispose();
        btnExitToMenu.dispose();
        for (TrashObject t : trash) t.dispose();
        for (BulletObject b : bullets) b.dispose();
        for (HealthPackObject h : healthPacks) h.dispose();
    }
}