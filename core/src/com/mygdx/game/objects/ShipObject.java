package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;
import com.mygdx.game.GameResources;

public class ShipObject extends GameObject {

    public ShipObject(float x, float y, float width, float height, World world) {
        super(x, y, width, height, GameResources.SHIP_IMG_PATH, world);
        body.setLinearDamping(10f);
    }

    public void move(Vector3 touch) {
        float fx = (touch.x - getX()) * GameSettings.SHIP_FORCE_RATIO;
        float fy = (touch.y - getY()) * GameSettings.SHIP_FORCE_RATIO;
        applyForceToCenter(fx, fy);
    }

    private void putInFrame() {

        if (getY() > (GameSettings.SCREEN_HEIGHT / 2f - height / 2f)) {
            setY(GameSettings.SCREEN_HEIGHT / 2f - height / 2f);
        }

        if (getY() <= (height / 2f)) {
            setY(height / 2f);
        }

        if (getX() < (-width / 2f)) {
            setX(GameSettings.SCREEN_WIDTH);
        }

        if (getX() > (GameSettings.SCREEN_WIDTH + width / 2f)) {
            setX(0);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        super.draw(batch);
    }
}