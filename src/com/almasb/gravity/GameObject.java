package com.almasb.gravity;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;

public abstract class GameObject extends Parent {

    public enum Type {
        PLAYER, PLATFORM, ENEMY, COIN, POWERUP, BULLET, SPIKE
    }

    private float width, height;
    protected boolean alive = true, dying = false;

    public GameObject(float x, float y, float w, float h) {
        this.setTranslateX(x);
        this.setTranslateY(y);
        width = w;
        height = h;
    }

    public abstract void onUpdate();
    public abstract void onDeath();
    public abstract void onCollide(GameObject other);
    public abstract Type getType();

    public boolean isAlive() {
        return alive;
    }

    public boolean isColliding(GameObject other) {
        return getTranslateX() + width >= other.getTranslateX() && getTranslateX() <= other.getTranslateX() + other.width
                && getTranslateY() + height >= other.getTranslateY() && getTranslateY() <= other.getTranslateY() + other.height;
    }
}
