package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;

public class GameSession {
    private long sessionStartTime, lastShotTime, nextTrashSpawnTime;
    private long pauseStartTime = 0;
    private long totalPausedTime = 0;
    public int score = 0;
    public int lives = GameSettings.SHIP_START_LIVES;
    public GameState state = GameState.PLAYING;
    private boolean isGameStarted = false;

    public void startGame() {
        isGameStarted = true;
        sessionStartTime = TimeUtils.millis();
        lastShotTime = sessionStartTime;
        nextTrashSpawnTime = sessionStartTime + GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void pauseGame() {
        if (state == GameState.PLAYING) {
            state = GameState.PAUSED;
            pauseStartTime = TimeUtils.millis();
        }
    }

    public void resumeGame() {
        if (state == GameState.PAUSED) {
            long pauseDuration = TimeUtils.millis() - pauseStartTime;
            totalPausedTime += pauseDuration;
            state = GameState.PLAYING;
        }
    }

    public long getCurrentTime() {
        return TimeUtils.millis() - totalPausedTime;
    }

    public boolean needToShoot() {
        long now = getCurrentTime();
        if (now - lastShotTime >= GameSettings.SHOOTING_COOL_DOWN) {
            lastShotTime = now;
            return true;
        }
        return false;
    }

    public boolean shouldSpawnTrash() {
        long now = getCurrentTime();
        if (now >= nextTrashSpawnTime) {
            long elapsed = now - sessionStartTime;
            float cooldown = Math.max(0.2f, 1f - Math.min(0.9f, elapsed / 60000f));
            nextTrashSpawnTime = now + (long)(GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN * cooldown);
            return true;
        }
        return false;
    }
}