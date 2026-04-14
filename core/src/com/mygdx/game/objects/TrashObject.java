package com.mygdx.game.objects;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;
import com.mygdx.game.GameResources;
import java.util.Random;

public class TrashObject extends GameObject {
    public boolean wasHit = false;

    public TrashObject(World world) {
        super(
                GameSettings.TRASH_WIDTH / 2f + 40 + new Random().nextInt(GameSettings.SCREEN_WIDTH - 80 - GameSettings.TRASH_WIDTH),
                GameSettings.SCREEN_HEIGHT + GameSettings.TRASH_HEIGHT / 2f,
                GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                GameResources.TRASH_IMG_PATH, GameSettings.CATEGORY_TRASH, world
        );
        setLinearVelocity(0, -GameSettings.TRASH_SPEED);
    }

    public boolean isInFrame() {
        return getY() + getHeight() / 2 > 0;
    }
}