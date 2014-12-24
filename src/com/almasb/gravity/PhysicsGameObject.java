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

    public enum Type {
        PLAYER, PLATFORM, ENEMY
    }

    public PhysicsGameObject(float x, float y, float width, float height, BodyType type, Image graphics) {
        super(x, y, graphics);

        bbox = new Rectangle(width, height);
        this.setTranslateX(x);
        this.setTranslateY(y);

        fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(toMeters(width / 2), toMeters(height / 2));
        fixtureDef.shape = shape;
        fixtureDef.density = 0.005f;
        fixtureDef.friction = 0.5f;

        bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(toMeters(x + width / 2), toMeters(Config.APP_H - (y + height / 2)));

        body = GameEnvironment.WORLD.createBody(bodyDef);
        body.createFixture(fixtureDef);
    }

    @Override
    public void onUpdate() {
        this.setTranslateX(toPixels(body.getPosition().x - toMeters(bbox.getWidth() / 2)));
        this.setTranslateY(toPixels(toMeters(Config.APP_H) - body.getPosition().y - toMeters(bbox.getHeight() / 2)));
        this.setRotate(-Math.toDegrees(body.getAngle()));
    }

    private float toMeters(double pixels) {
        return (float)pixels * 0.05f;
    }

    private float toPixels(double meters) {
        return (float)meters * 20f;
    }
}
