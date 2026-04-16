package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageView {
    Texture texture;
    float x, y;

    public ImageView(float x, float y, String path) {
        this.x = x;
        this.y = y;
        texture = new Texture(path);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void dispose() { texture.dispose(); }
}