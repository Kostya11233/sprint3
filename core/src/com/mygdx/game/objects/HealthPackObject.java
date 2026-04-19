package com.mygdx.game.objects;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;
import com.mygdx.game.GameResources;
import java.util.Random;

public class HealthPackObject extends GameObject {
    public boolean wasHit = false;
    public boolean isCollected = false;

    public HealthPackObject(World world) {
        super(
                GameSettings.HEALTH_PACK_WIDTH / 2f + 40 + new Random().nextInt(GameSettings.SCREEN_WIDTH - 80 - GameSettings.HEALTH_PACK_WIDTH),
                GameSettings.SCREEN_HEIGHT + GameSettings.HEALTH_PACK_HEIGHT / 2f,
                GameSettings.HEALTH_PACK_WIDTH, GameSettings.HEALTH_PACK_HEIGHT,
                GameResources.HEALTH_PACK_IMG_PATH, GameSettings.CATEGORY_HEALTH_PACK, world
        );
        setLinearVelocity(0, -GameSettings.HEALTH_PACK_SPEED);
    }

    public boolean isInFrame() {
        return getY() + getHeight() / 2 > 0;
    }
}