package com.almasb.gravity;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameObject extends Parent {

    protected ImageView sprite;

    public GameObject(float x, float y, Image graphics) {
        this.setTranslateX(x);
        this.setTranslateY(y);

        sprite = new ImageView(graphics);

        getChildren().add(sprite);
    }

    public abstract void onUpdate();
}
