package com.mygdx.game;

public class GameSettings {
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;

    public static final float SHIP_WIDTH = 80;
    public static final float SHIP_HEIGHT = 80;
    public static final float SHIP_FORCE_RATIO = 15f;
    public static final int SHIP_START_LIVES = 3;

    public static final int TRASH_WIDTH = 60;
    public static final int TRASH_HEIGHT = 60;
    public static final float TRASH_SPEED = 200f;
    public static final long STARTING_TRASH_APPEARANCE_COOL_DOWN = 1500;

    public static final int BULLET_WIDTH = 20;
    public static final int BULLET_HEIGHT = 20;
    public static final float BULLET_SPEED = 500f;
    public static final long SHOOTING_COOL_DOWN = 400;

    public static final float STEP_TIME = 1 / 60f;

    public static final short CATEGORY_SHIP = 1;
    public static final short CATEGORY_BULLET = 2;
    public static final short CATEGORY_TRASH = 4;
}