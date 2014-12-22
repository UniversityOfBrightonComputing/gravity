package com.almasb.gravity;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;

public class JBoxRectangle extends Parent {

    public Rectangle rect;
    public FixtureDef fixtureDef;
    public BodyDef bodyDef;
    public Body body;

    public JBoxRectangle(float x, float y, float width, float height) {
        rect = new Rectangle(width, height);
        this.setTranslateX(x);
        this.setTranslateY(y);

        getChildren().add(rect);

        fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        fixtureDef.shape = shape;
        fixtureDef.density = 0.005f;
        fixtureDef.friction = 0.5f;

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(x + width / 2, Config.APP_H - (y + height / 2));
    }

    public void update() {
        this.setTranslateX(body.getPosition().x - rect.getWidth() / 2);
        this.setTranslateY(Config.APP_H - body.getPosition().y - rect.getHeight() / 2);
        this.setRotate(-Math.toDegrees(body.getAngle()));
    }
}
