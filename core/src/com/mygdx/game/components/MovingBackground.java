package com.mygdx.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class MovingBackground {
    Texture texture;
    float y1, y2;
    float speed = 100f;

    public MovingBackground(String path) {
        texture = new Texture(path);
        y1 = 0;
        y2 = GameSettings.SCREEN_HEIGHT;
    }

    public void move() {
        y1 -= speed * Gdx.graphics.getDeltaTime();
        y2 -= speed * Gdx.graphics.getDeltaTime();
        if (y1 <= -GameSettings.SCREEN_HEIGHT) y1 = GameSettings.SCREEN_HEIGHT;
        if (y2 <= -GameSettings.SCREEN_HEIGHT) y2 = GameSettings.SCREEN_HEIGHT;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, 0, y1, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        batch.draw(texture, 0, y2, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
    }

    public void dispose() { texture.dispose(); }
}