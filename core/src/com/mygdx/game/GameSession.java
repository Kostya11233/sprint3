package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;

public class GameSession {
    private long sessionStartTime, lastShotTime, nextTrashSpawnTime;
    public int score = 0;
    public int lives = GameSettings.SHIP_START_LIVES;

    public void startGame() {
        sessionStartTime = TimeUtils.millis();
        lastShotTime = sessionStartTime;
        nextTrashSpawnTime = sessionStartTime + GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN;
    }

    public boolean needToShoot() {
        long now = TimeUtils.millis();
        if (now - lastShotTime >= GameSettings.SHOOTING_COOL_DOWN) {
            lastShotTime = now;
            return true;
        }
        return false;
    }

    public boolean shouldSpawnTrash() {
        long now = TimeUtils.millis();
        if (now >= nextTrashSpawnTime) {
            long elapsed = now - sessionStartTime;
            float cooldown = Math.max(0.2f, 1f - Math.min(0.9f, elapsed / 60000f));
            nextTrashSpawnTime = now + (long)(GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN * cooldown);
            return true;
        }
        return false;
    }
}