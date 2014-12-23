package com.almasb.gravity;

import javafx.geometry.Rectangle2D;

import org.jbox2d.dynamics.BodyType;

public class Player extends PhysicsGameObject {

    public Player(float x, float y) {
        super(x, y, 40, 40, BodyType.DYNAMIC, Config.Image.PLAYER);
        this.body.setFixedRotation(true);
        sprite.setViewport(new Rectangle2D(0, 120, 40, 40));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();


    }
}
