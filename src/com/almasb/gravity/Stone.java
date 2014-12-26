package com.almasb.gravity;

import javafx.scene.image.ImageView;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class Stone extends GameObject {

    public Stone(float x, float y) {
        super(x, y, 40, 40, BodyType.DYNAMIC, true);

        ImageView sprite = new ImageView(Config.Image.STONE);
        sprite.setFitHeight(40);
        sprite.setFitWidth(40);

        getChildren().add(sprite);

        this.setOnMouseDragged(event -> {
            double x2 = -GameEnvironment.LEVEL_ROOT.getLayoutX() + event.getSceneX();
            double y2 = event.getSceneY();

            body.setLinearVelocity(new Vec2(toMeters(x2), toMeters(Config.APP_H - y2)).sub(body.getPosition()));
        });
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
        return Type.STONE;
    }
}