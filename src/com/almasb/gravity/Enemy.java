package com.almasb.gravity;

import javafx.geometry.Rectangle2D;

import org.jbox2d.dynamics.BodyType;

public class Enemy extends PhysicsGameObject {

    public Enemy(float x, float y) {
        super(x, y, 40, 40, BodyType.DYNAMIC, Config.Image.ENEMY);
        sprite.setViewport(new Rectangle2D(0, 120, 40, 40));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();


    }
}
