package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameSettings;

public class GameObject {
    protected Texture texture;
    protected float width, height;
    protected Body body;

    private static final float SCALE = 0.05f;

    public GameObject(float x, float y, float width, float height, String texturePath, World world) {
        this.width = width;
        this.height = height;
        this.texture = new Texture(texturePath);

        createBody(x, y, world);
    }

    private void createBody(float x, float y, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        body = world.createBody(def);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Math.max(width, height) * SCALE / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;

        body.createFixture(fixtureDef);
        circleShape.dispose();

        body.setTransform(x * SCALE, y * SCALE, 0);
    }

    public void draw(SpriteBatch batch) {
        float x = getX();
        float y = getY();
        batch.draw(texture, x - width / 2, y - height / 2, width, height);
    }

    public void dispose() {
        texture.dispose();
        if (body != null && body.getWorld() != null) {
            body.getWorld().destroyBody(body);
        }
    }


    public float getX() {
        return body.getPosition().x / SCALE;
    }

    public float getY() {
        return body.getPosition().y / SCALE;
    }

    public void setX(float x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(float y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    public void applyForceToCenter(float fx, float fy) {
        body.applyForceToCenter(fx, fy, true);
    }
}