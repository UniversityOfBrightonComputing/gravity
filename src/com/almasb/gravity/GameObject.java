package com.almasb.gravity;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;

public abstract class GameObject extends Parent {

    protected boolean alive = true, dying = false;

    public GameObject(float x, float y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public abstract void onUpdate();
    public abstract void onDeath();

    public boolean isAlive() {
        return alive;
    }
}
