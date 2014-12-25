package com.almasb.gravity;

import javafx.scene.shape.Rectangle;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import com.almasb.gravity.Enemy.Direction;

public class Bullet extends PhysicsGameObject {

    private Direction direction;

    public Bullet(float x, float y, Direction dir) {
        super(x, y, 5, 1, BodyType.DYNAMIC);
        body.setBullet(true);
        body.setGravityScale(0.0f);
        body.setUserData(this);
        direction = dir;

        getChildren().add(new Rectangle(5, 1));

        this.body.setLinearVelocity((new Vec2(direction == Direction.LEFT ? -25 : 25, 0)));
    }

    @Override
    public void onUpdate() {
        if (dying) {
            if (!bodyDestroyed) {
                System.out.println("acall");
                GameEnvironment.WORLD.destroyBody(body);
                bodyDestroyed = true;
                alive = false;
            }
            return;
        }

        super.onUpdate();
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
