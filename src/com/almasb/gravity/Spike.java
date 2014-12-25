package com.almasb.gravity;

import org.jbox2d.dynamics.BodyType;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Region;

public class Spike extends GameObject {

    public Spike(float x, float y, float w, float h) {
        super(x, y, w, h, BodyType.STATIC, true);

        Region r = new Region();
        r.setPrefSize(w, h);
        r.setBackground(new Background(new BackgroundImage(Config.Image.SPIKE, null, null, null, null)));

        getChildren().add(r);
    }


    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }

    @Override
    public Type getType() {
        return Type.SPIKE;
    }

    @Override
    public void onCollide(GameObject other) {
        other.onDeath();
    }


    @Override
    protected void onUpdate() {
        // TODO Auto-generated method stub

    }
}
