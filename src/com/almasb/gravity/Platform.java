package com.almasb.gravity;

import javafx.geometry.Rectangle2D;

import org.jbox2d.dynamics.BodyType;

public class Platform extends PhysicsGameObject {

    public Platform(float x, float y) {
        super(x, y, 40, 40, BodyType.STATIC, Config.Image.PLATFORM);
        sprite.setViewport(new Rectangle2D(0, 0, 40, 40));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();


    }
}
