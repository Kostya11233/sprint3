package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Buton {
    public BitmapFont font;
    public String text;
    public Texture texture;
    public float x, y, width, height;
    public float textX, textY;

    public Buton(float x, float y, float width, float height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        font = new BitmapFont();
        font.getData().setScale(2f);
        font.setColor(Color.WHITE);
        updateTextPosition();
        texture = new Texture("textures/button_background_short.png");
    }

    public void setText(String text) {
        this.text = text;
        updateTextPosition();
    }

    private void updateTextPosition() {
        GlyphLayout gl = new GlyphLayout(font, text);
        textX = x + (width - gl.width) / 2;
        textY = y + (height + gl.height) / 2;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
        font.draw(batch, text, textX, textY);
    }


    public boolean isHit(float tx, float ty) {
        float realY = ty;
        return tx >= x && tx <= x + width && realY >= y && realY <= y + height;
    }


    public void dispose() {
        texture.dispose();
        font.dispose();
    }
}