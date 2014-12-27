package com.almasb.gravity;

import javafx.scene.shape.Rectangle;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import com.almasb.gravity.Enemy.Direction;

public class Bullet extends GameObject {

    private Direction direction;

    // TODO: add max distance after each to die

    public Bullet(float x, float y, Vec2 velocity) {
        super(x, y, 5, 1, BodyType.DYNAMIC, true);
        body.setBullet(true);
        body.setGravityScale(0.0f);

        //direction = dir;

        getChildren().add(new Rectangle(5, 1));

        //this.body.setLinearVelocity((new Vec2(direction == Direction.LEFT ? -25 : 25, 0)));
        body.setLinearVelocity(velocity);
    }

    @Override
    public void onUpdate() {
        if (dying) {
            if (!bodyDestroyed) {
                GameEnvironment.getWorld().destroyBody(body);
                bodyDestroyed = true;
                alive = false;
            }
            return;
        }
    }

    @Override
    public void onDeath() {
        dying = true;
    }

    @Override
    public void onCollide(GameObject other) {
        onDeath();
    }

    @Override
    public Type getType() {
        return Type.BULLET;
    }
}
