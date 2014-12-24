package com.almasb.gravity;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
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

    public PhysicsGameObject(float x, float y, float width, float height, BodyType type) {
        super(x, y);

        bbox = new Rectangle(width, height);
        bbox.setFill(null);

        getChildren().add(bbox);

        fixtureDef = new FixtureDef();

        //        if (this instanceof Platform) {
        //            Vec2[] vs = new Vec2[4];
        //            vs[0] = new Vec2(toMeters(-width/2), toMeters(-height/2));
        //            vs[1] = new Vec2(toMeters(width/2), toMeters(-height/2));
        //            vs[2] = new Vec2(toMeters(width/2), toMeters(height/2));
        //            vs[3] = new Vec2(toMeters(-width/2), toMeters(height/2));
        //            ChainShape shape = new ChainShape();
        //            shape.createLoop(vs, 4);
        //
        //            fixtureDef.shape = shape;
        //        }
        //        else {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(toMeters(width / 2), toMeters(height / 2));

        fixtureDef.shape = shape;
        //}

        fixtureDef.density = 0.005f;
        fixtureDef.friction = 0.5f;



        bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(toMeters(x + width / 2), toMeters(Config.APP_H - (y + height / 2)));

        body = GameEnvironment.WORLD.createBody(bodyDef);
        body.createFixture(fixtureDef);

        //        Vec2 topLeft = new Vec2(0 - toMeters(width/2), toMeters(height/2));
        //        Vec2 topRight = new Vec2(0 + toMeters(width/2), toMeters(height/2));
        //        Vec2 botLeft = new Vec2(0 - toMeters(width/2), 0 - toMeters(height/2));
        //        Vec2 botRight = new Vec2(0 + toMeters(width/2), 0 - toMeters(height/2));

        //        EdgeShape shape = new EdgeShape();
        //
        //        shape.set(topLeft, topRight);
        //        fixtureDef.shape = shape;
        //        body.createFixture(fixtureDef);
        //
        //        shape.set(topRight, botRight);
        //        fixtureDef.shape = shape;
        //        body.createFixture(fixtureDef);
        //
        //        shape.set(botRight, botLeft);
        //        fixtureDef.shape = shape;
        //        body.createFixture(fixtureDef);
        //
        //        shape.set(botLeft, topLeft);
        //        fixtureDef.shape = shape;
        //        body.createFixture(fixtureDef);
    }

    @Override
    public void onUpdate() {
        bbox.setStroke(body.isAwake() ? Color.GOLD : Color.RED);

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
