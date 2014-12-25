package com.almasb.gravity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.dynamics.BodyType;

public class Player extends GameObject {

    public final SimpleIntegerProperty maxPower = new SimpleIntegerProperty(1000);
    public final SimpleIntegerProperty power = new SimpleIntegerProperty();

    public final SimpleIntegerProperty score = new SimpleIntegerProperty();
    public final SimpleIntegerProperty health = new SimpleIntegerProperty();

    private int powerRegen = 1;

    private ImageView sprite;

    public Player(float x, float y) {
        super(x, y, 38, 38, BodyType.DYNAMIC, true);
        this.body.setFixedRotation(true);

        sprite = new ImageView(Config.Image.PLAYER);
        sprite.setViewport(new Rectangle2D(0, 120, 40, 40));

        getChildren().add(sprite);

        health.set(10);
        health.addListener((obs, old, newValue) -> {
            if (newValue.intValue() == 0)
                onDeath();
        });
    }

    @Override
    public void onUpdate() {
        power.set(Math.min(power.get() + powerRegen, maxPower.get()));
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }

    @Override
    public Type getType() {
        return Type.PLAYER;
    }

    @Override
    public void onCollide(GameObject other) {
        switch (other.getType()) {
            case BULLET:
                health.set(health.get() - 1);
                break;
            case COIN:
                score.set(score.get() + Config.SCORE_COIN);
                Config.Audio.COIN.play();
                break;
            case POWERUP:
                score.set(score.get() + Config.SCORE_POWERUP);
                break;
        }
    }
}
