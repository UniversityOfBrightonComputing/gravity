package com.almasb.gravity;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Region;

import org.jbox2d.dynamics.BodyType;

import com.almasb.gravity.PhysicsGameObject.Type;

public class Platform extends PhysicsGameObject {

    public Platform(float x, float y, float w, float h) {
        super(x, y, w, h, BodyType.STATIC);
        this.body.setUserData(this);

        Region r = new Region();
        r.setPrefSize(w, h);
        r.setBackground(new Background(new BackgroundImage(Config.Image.PLATFORM, null, null, null, null)));

        getChildren().add(r);
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }
}
