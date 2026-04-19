package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;

public class GameSession {
    private long sessionStartTime, lastShotTime, nextTrashSpawnTime, nextHealthPackSpawnTime;
    private int destructedTrashNumber = 0;
    private boolean isGameStarted = false;
    public int lives = GameSettings.SHIP_START_LIVES;

    private int scoreForNextHealthPack = 20;
    private boolean healthPackSpawnRequested = false;

    public void startGame() {
        isGameStarted = true;
        sessionStartTime = TimeUtils.millis();
        lastShotTime = sessionStartTime;
        nextTrashSpawnTime = sessionStartTime + GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN;
        nextHealthPackSpawnTime = sessionStartTime + GameSettings.HEALTH_PACK_SPAWN_COOLDOWN;
    }

    public boolean isGameStarted() { return isGameStarted; }

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


    public boolean shouldSpawnHealthPack() {
        long now = TimeUtils.millis();
        if (healthPackSpawnRequested && now >= nextHealthPackSpawnTime) {
            healthPackSpawnRequested = false;
            nextHealthPackSpawnTime = now + GameSettings.HEALTH_PACK_SPAWN_COOLDOWN;
            return true;
        }
        return false;
    }


    public void destructionRegistration() {
        destructedTrashNumber++;


        if (getScore() >= scoreForNextHealthPack) {
            scoreForNextHealthPack += 20;
            healthPackSpawnRequested = true;
            nextHealthPackSpawnTime = TimeUtils.millis() + GameSettings.HEALTH_PACK_DELAY_AFTER_SCORE;
        }
    }


    public void onShipHit() {

    }


    public void onHealthPackCollected() {

        if (lives > GameSettings.MAX_LIVES) {
            lives = GameSettings.MAX_LIVES;
        }
    }

    public int getScore() {
        return destructedTrashNumber * 1;
    }

    public void endGame() {
        isGameStarted = false;
        ArrayList<Integer> records = MemoryManager.loadRecords();
        int finalScore = getScore();
        int idx = 0;
        while (idx < records.size() && records.get(idx) > finalScore) idx++;
        records.add(idx, finalScore);
        if (records.size() > 7) records.remove(7);
        MemoryManager.saveRecords(records);
    }
}