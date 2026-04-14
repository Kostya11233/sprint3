package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;
import com.mygdx.game.GameResources;

public class ShipObject extends GameObject {
    public ShipObject(float x, float y, World world) {
        super(x, y, GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH, GameSettings.CATEGORY_SHIP, world);
        body.setLinearDamping(10f);
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
        super.draw(batch);
    }
}