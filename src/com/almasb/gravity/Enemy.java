package com.almasb.gravity;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import com.almasb.gravity.PhysicsGameObject.Type;

public class Enemy extends PhysicsGameObject {

    private enum Direction {
        LEFT, RIGHT
    }

    private Direction direction = Direction.LEFT;

    private int tick = 0;
    private int frame = 0, place = 0;

    int FACTOR = 15;

    private boolean isManipulated = false;

    // debug
    private DropShadow drop = new DropShadow(3, Color.BLACK);

    public Enemy(float x, float y) {
        super(x, y, 40, 40, BodyType.DYNAMIC, Config.Image.ENEMY);
        this.body.setUserData(this);
        sprite.setViewport(new Rectangle2D(0, 120, 40, 40));

        drop.setInput(new Glow());
    }

    @Override
    public void onUpdate() {
        if (!alive || dying)
            return;

        super.onUpdate();

        // debug
        if (body.isAwake()) {
            sprite.setEffect(drop);
        }
        else {
            sprite.setEffect(null);
        }

        if (!body.isAwake() && Math.round(this.getRotate()) == 0) {
            isManipulated = false;
        }

        if (!isManipulated) {
            this.body.setLinearVelocity((new Vec2(direction == Direction.LEFT ? -3 : 3, 0)));

            tick++;
            if (tick == 2 * Config.SECOND) {
                direction = Direction.RIGHT;
            }
            else if (tick == 4 * Config.SECOND) {
                direction = Direction.LEFT;
                tick = 0;
            }

            frame++;

            if (frame == 4 * FACTOR)
                frame = 0;

            if (frame / FACTOR == 0 || frame / FACTOR == 2)
                place = 0;
            if (frame / FACTOR == 1)
                place = 1;
            if (frame / FACTOR == 3)
                place = 2;
        }

        sprite.setViewport(new Rectangle2D(place * 40, 80 + 40 * direction.ordinal(), 40, 40));
    }

    @Override
    public void onDeath() {
        dying = true;
        GameEnvironment.WORLD.destroyBody(body);
        sprite.setImage(Config.Image.EXPLOSION);
        sprite.setViewport(new Rectangle2D(0, 0, 40, 40));

        SimpleIntegerProperty frameProperty = new SimpleIntegerProperty();
        frameProperty.addListener((obs, old, newValue) -> {
            if (newValue.intValue() > old.intValue())
                sprite.setViewport(new Rectangle2D((newValue.intValue() % 5) * 40, (newValue.intValue() / 5) * 40, 40, 40));
        });

        Timeline t = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(frameProperty, 24)));
        t.setOnFinished(event -> {
            alive = false;
        });
        t.play();
    }

    public void setUnstable() {
        isManipulated = true;
    }
}
