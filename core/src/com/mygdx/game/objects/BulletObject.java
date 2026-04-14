package com.mygdx.game.objects;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;
import com.mygdx.game.GameResources;

public class BulletObject extends GameObject {
    public boolean wasHit = false;

    public BulletObject(float x, float y, World world) {
        super(x, y, GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                GameResources.BULLET_IMG_PATH, GameSettings.CATEGORY_BULLET, world);
        setLinearVelocity(0, GameSettings.BULLET_SPEED);
    }

    public boolean hasToBeDestroyed() {
        return getY() + getHeight() / 2 > GameSettings.SCREEN_HEIGHT;
    }
}