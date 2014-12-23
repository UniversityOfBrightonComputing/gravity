package com.almasb.gravity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public abstract class PhysicsGameObject extends GameObject {

    // TODO: remove
    private Rectangle bbox;

    protected FixtureDef fixtureDef;
    protected BodyDef bodyDef;
    protected Body body;

    public PhysicsGameObject(float x, float y, float width, float height, BodyType type, Image graphics) {
        super(x, y, graphics);

        bbox = new Rectangle(width, height);
        this.setTranslateX(x);
        this.setTranslateY(y);

        fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        fixtureDef.shape = shape;
        fixtureDef.density = 0.005f;
        fixtureDef.friction = 0.5f;

        bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x + width / 2, Config.APP_H - (y + height / 2));

        body = GameEnvironment.WORLD.createBody(bodyDef);
        body.createFixture(fixtureDef);
    }

    @Override
    public void onUpdate() {
        this.setTranslateX(body.getPosition().x - bbox.getWidth() / 2);
        this.setTranslateY(Config.APP_H - body.getPosition().y - bbox.getHeight() / 2);
        this.setRotate(-Math.toDegrees(body.getAngle()));
    }
}
