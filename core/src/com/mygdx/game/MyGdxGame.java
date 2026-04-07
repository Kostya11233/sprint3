package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

public class MyGdxGame extends Game {

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public GameScreen gameScreen;
	public World world;
	public Vector3 touch = new Vector3();

	private float accumulator = 0;
	private final float STEP_TIME = 1 / 60f;

	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);


		world = new World(new Vector2(0, 0), true);

		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	public void stepWorld() {
		accumulator += Gdx.graphics.getDeltaTime();
		while (accumulator >= STEP_TIME) {
			world.step(STEP_TIME, 6, 2);
			accumulator -= STEP_TIME;
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		world.dispose();
	}
}