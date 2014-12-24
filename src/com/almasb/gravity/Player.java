package com.almasb.gravity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.dynamics.BodyType;

public class Player extends PhysicsGameObject {

    private final SimpleIntegerProperty maxPower = new SimpleIntegerProperty(1000);
    private final SimpleIntegerProperty power = new SimpleIntegerProperty();

    private int powerRegen = 1;

    public Player(float x, float y) {
        super(x, y, 40, 40, BodyType.DYNAMIC, Config.Image.PLAYER);
        this.body.setFixedRotation(true);
        EdgeShape d;
        this.body.setUserData(this);
        sprite.setViewport(new Rectangle2D(0, 120, 40, 40));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();


        power.set(Math.min(power.get() + powerRegen, maxPower.get()));
    }

    public SimpleIntegerProperty maxPowerProperty() {
        return maxPower;
    }

    public SimpleIntegerProperty powerProperty() {
        return power;
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }
}
