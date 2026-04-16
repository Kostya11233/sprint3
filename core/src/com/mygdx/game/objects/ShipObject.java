package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

public class ShipObject extends GameObject {
    private Texture fireTexture;

    public ShipObject(float x, float y, World world) {
        super(x, y, GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH, GameSettings.CATEGORY_SHIP, world);
        body.setLinearDamping(10f);
        fireTexture = new Texture(GameResources.FIRE_IMG_PATH);
    }

    public void move(Vector3 touch) {
        float fx = (touch.x - getX()) * GameSettings.SHIP_FORCE_RATIO;
        float fy = (touch.y - getY()) * GameSettings.SHIP_FORCE_RATIO;
        applyForceToCenter(fx, fy);
    }

    private void putInFrame() {
        if (getY() > GameSettings.SCREEN_HEIGHT / 2f - getHeight() / 2f)
            setY(GameSettings.SCREEN_HEIGHT / 2f - getHeight() / 2f);
        if (getY() < getHeight() / 2f) setY(getHeight() / 2f);
        if (getX() < -getWidth() / 2f) setX(GameSettings.SCREEN_WIDTH);
        if (getX() > GameSettings.SCREEN_WIDTH + getWidth() / 2f) setX(0);
    }

    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        float fireWidth = getWidth() * 0.7f;
        float fireHeight = getHeight() * 0.4f;
        float fireX = getX() - fireWidth / 2;
        float fireY = getY() - getHeight() / 2 - fireHeight + 10;
        batch.draw(fireTexture, fireX, fireY, fireWidth, fireHeight);
        super.draw(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        fireTexture.dispose();
    }
}