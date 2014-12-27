package com.almasb.gravity;

import org.jbox2d.dynamics.BodyType;

public class Viewport extends GameObject {

    public Viewport() {
        super(0, 0, Config.APP_W, Config.APP_H, null, false);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onUpdate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCollide(GameObject other) {
        // TODO Auto-generated method stub

    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }

}
