package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    public Music backgroundMusic;
    public Sound shootSound;
    public Sound explosionSound;
    public boolean isMusicOn;
    public boolean isSoundOn;
    private boolean musicPlaying = false;

    public AudioManager() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKGROUND_MUSIC_PATH));
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.EXPLOSION_SOUND_PATH));

        backgroundMusic.setVolume(0.5f);
        backgroundMusic.setLooping(true);

        isMusicOn = MemoryManager.loadIsMusicOn();
        isSoundOn = MemoryManager.loadIsSoundOn();

        applyMusicSettings();
    }

    private void applyMusicSettings() {
        if (isMusicOn && !musicPlaying) {
            backgroundMusic.play();
            musicPlaying = true;
        } else if (!isMusicOn && musicPlaying) {
            backgroundMusic.pause();
            musicPlaying = false;
        }
    }

    public void updateMusicFlag() {
        isMusicOn = MemoryManager.loadIsMusicOn();
        applyMusicSettings();
    }

    public void toggleMusic() {
        isMusicOn = !isMusicOn;
        MemoryManager.saveMusicSettings(isMusicOn);
        applyMusicSettings();
    }

    public void toggleSound() {
        isSoundOn = !isSoundOn;
        MemoryManager.saveSoundSettings(isSoundOn);
    }

    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
        if (shootSound != null) shootSound.dispose();
        if (explosionSound != null) explosionSound.dispose();
    }
}