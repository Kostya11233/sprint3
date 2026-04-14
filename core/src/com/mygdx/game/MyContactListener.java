package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.objects.BulletObject;
import com.mygdx.game.objects.ShipObject;
import com.mygdx.game.objects.TrashObject;

public class MyContactListener implements ContactListener {
    private GameSession gameSession;

    public MyContactListener(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        Object ua = a.getUserData();
        Object ub = b.getUserData();

        if (ua instanceof BulletObject && ub instanceof TrashObject) handle((BulletObject)ua, (TrashObject)ub);
        else if (ua instanceof TrashObject && ub instanceof BulletObject) handle((BulletObject)ub, (TrashObject)ua);
        else if (ua instanceof ShipObject && ub instanceof TrashObject) handle((ShipObject)ua, (TrashObject)ub);
        else if (ua instanceof TrashObject && ub instanceof ShipObject) handle((ShipObject)ub, (TrashObject)ua);
    }

    private void handle(BulletObject bullet, TrashObject trash) {
        if (!bullet.wasHit && !trash.wasHit) {
            bullet.wasHit = true;
            trash.wasHit = true;
            gameSession.score ++;

        }


    }

    private void handle(ShipObject ship, TrashObject trash) {
        if (!trash.wasHit) {
            trash.wasHit = true;
            gameSession.lives--;
        }
    }

    @Override public void endContact(Contact contact) {}
    @Override public void preSolve(Contact contact, Manifold oldManifold) {}
    @Override public void postSolve(Contact contact, ContactImpulse impulse) {}
}