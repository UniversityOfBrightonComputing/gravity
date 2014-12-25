package com.almasb.gravity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import com.almasb.gravity.Enemy.Direction;

public class App extends GameEnvironment {
    private Player player = new Player(120, 400);

    private World world = WORLD;

    final float SPEED = 10;
    float gravity = 8;

    @Override
    protected Parent createContent() {
        initLevel(0);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body body1 = contact.getFixtureA().getBody();
                Body body2 = contact.getFixtureB().getBody();

                ((GameObject)body1.getUserData()).onCollide((GameObject)body2.getUserData());
                ((GameObject)body2.getUserData()).onCollide((GameObject)body1.getUserData());
            }

            @Override
            public void endContact(Contact contact) {}
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });

        ProgressBar gBar = new ProgressBar();
        gBar.setTranslateX(50);
        gBar.setTranslateY(50);
        gBar.progressProperty().bind(player.power.divide(player.maxPower.multiply(1.0f)));

        Text score = new Text();
        score.setTranslateX(50);
        score.setTranslateY(25);
        score.setFont(Config.FONT);
        score.setFill(Color.GOLD);
        score.textProperty().bind(player.score.asString());

        UI_ROOT.getChildren().addAll(score, gBar, createInfo());

        Pane appRoot = new Pane();
        appRoot.getChildren().addAll(LEVEL_ROOT, UI_ROOT);
        return appRoot;
    }

    private void initLevel(int levelNumber) {
        // TODO: clean level after new inited
        Level level = new Level(Config.Text.LEVEL0);

        level.gameObjects.add(player);
        LEVEL_OBJECTS.setAll(level.gameObjects);
        LEVEL_ROOT.getChildren().setAll(level.gameObjects);
    }

    Text debug = new Text();

    private VBox createInfo() {
        VBox vbox = new VBox(10);
        vbox.setTranslateX(900);
        vbox.setTranslateY(200);

        vbox.getChildren().addAll(
                new Text("WASD - gravity defying movement"),
                new Text("G - alter gravity"),
                new Text("Q - Force 'pull'"),
                new Text("E - Force 'push"),
                debug);

        return vbox;
    }

    @Override
    protected void update() {
        if (keys[UP] && player.power.get() >= skillCosts[UP]) {
            player.power.set(player.power.get() - skillCosts[UP]);
            player.body.setLinearVelocity(new Vec2(player.body.getLinearVelocity().x, SPEED));
        }

        if (keys[DOWN]) {
            player.body.setLinearVelocity(new Vec2(player.body.getLinearVelocity().x, -SPEED));
        }

        if (keys[LEFT]) {
            player.body.setLinearVelocity(new Vec2(-SPEED, player.body.getLinearVelocity().y));
        }

        if (keys[RIGHT]) {
            player.body.setLinearVelocity(new Vec2(SPEED, player.body.getLinearVelocity().y));
        }

        if (keys[GRAVITY] && player.power.get() >= skillCosts[GRAVITY]) {
            player.power.set(player.power.get() - skillCosts[GRAVITY]);
            world.setGravity(new Vec2(0, gravity));
        }
        else {
            world.setGravity(new Vec2(0, -10));
        }

        world.step(Config.TIME_STEP, 8, 3);

        for (GameObject obj : LEVEL_OBJECTS) {
            obj.update();
        }

        // clean update list
        LEVEL_OBJECTS.removeIf(obj -> !obj.isAlive());

        // clean view list
        LEVEL_ROOT.getChildren().removeIf(node -> !((GameObject)node).isAlive());

        debug.setText("Body count: " + world.getBodyCount());
    }

    @Override
    protected void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE:
                Bullet b = new Bullet((float)player.getTranslateX() + 40, (float)player.getTranslateY(), Direction.RIGHT);
                LEVEL_OBJECTS.add(b);
                LEVEL_ROOT.getChildren().add(b);
                break;
        }

        if (keys[PULL] && player.power.get() >= skillCosts[PULL]) {
            player.power.set(player.power.get() - skillCosts[PULL]);
            for (GameObject obj : LEVEL_OBJECTS) {
                if (obj instanceof Enemy) {
                    ((Enemy)obj).setUnstable();
                }
                obj.body.applyForceToCenter((obj.body.getPosition().sub(player.body.getPosition()).mul(-30)));
            }
        }

        if (keys[PUSH] && player.power.get() >= skillCosts[PUSH]) {
            player.power.set(player.power.get() - skillCosts[PUSH]);
            for (GameObject obj : LEVEL_OBJECTS) {
                if (obj instanceof Enemy) {
                    ((Enemy)obj).setUnstable();
                }
                obj.body.applyForceToCenter((obj.body.getPosition().sub(player.body.getPosition()).mul(30)));
            }
        }
    }

    @Override
    protected void handleKeyReleased(KeyEvent event) {}
}
