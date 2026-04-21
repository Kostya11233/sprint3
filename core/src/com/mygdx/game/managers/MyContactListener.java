package com.mygdx.game.managers;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSettings;
import com.mygdx.game.objects.*;
import com.mygdx.game.AudioManager;

public class MyContactListener implements ContactListener {
    GameSession session;
    AudioManager audioManager;

    public MyContactListener(GameSession session, AudioManager audioManager) {
        this.session = session;
        this.audioManager = audioManager;
    }

    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        // Пуля + Мусор
        if (a instanceof BulletObject && b instanceof TrashObject)
            handle((BulletObject) a, (TrashObject) b);
        else if (a instanceof TrashObject && b instanceof BulletObject)
            handle((BulletObject) b, (TrashObject) a);

            // Корабль + Мусор
        else if (a instanceof ShipObject && b instanceof TrashObject)
            handle((ShipObject) a, (TrashObject) b);
        else if (a instanceof TrashObject && b instanceof ShipObject)
            handle((ShipObject) b, (TrashObject) a);

            // Корабль + Аптечка
        else if (a instanceof ShipObject && b instanceof HealthPackObject)
            handle((ShipObject) a, (HealthPackObject) b);
        else if (a instanceof HealthPackObject && b instanceof ShipObject)
            handle((ShipObject) b, (HealthPackObject) a); 

            // Пуля + Аптечка
        else if (a instanceof BulletObject && b instanceof HealthPackObject)
            handle((BulletObject) a, (HealthPackObject) b);
        else if (a instanceof HealthPackObject && b instanceof BulletObject)
            handle((BulletObject) b, (HealthPackObject) a);
    }

    void handle(BulletObject b, TrashObject t) {
        if (!b.wasHit && !t.wasHit) {
            b.wasHit = true;
            t.wasHit = true;
            session.destructionRegistration();
            if (audioManager != null && audioManager.explosionSound != null) {
                audioManager.explosionSound.play();
            }
        }
    }

    void handle(ShipObject s, TrashObject t) {
        if (!t.wasHit) {
            t.wasHit = true;
            session.lives--;
            System.out.println("Ship hit! Lives: " + session.lives);
            if (audioManager != null && audioManager.explosionSound != null) {
                audioManager.explosionSound.play();
            }
        }
    }


    void handle(ShipObject s, HealthPackObject h) {
        if (!h.isCollected) {
            h.isCollected = true;
            session.lives++;


            if (session.lives > GameSettings.MAX_LIVES) {
                session.lives = GameSettings.MAX_LIVES;
            }

            System.out.println("Health pack collected! Lives: " + session.lives);


        }
    }

    void handle(BulletObject b, HealthPackObject h) {
        if (!h.isCollected && !b.wasHit) {
            h.isCollected = true;
            b.wasHit = true;
            System.out.println("Health pack destroyed by bullet!");
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}