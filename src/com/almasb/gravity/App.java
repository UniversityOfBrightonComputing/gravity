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
    /**
     * Elements in gameRoot are affected by camera control
     * Elements in uiRoot are always on the screen and do not move
     */
    private Group gameRoot = new Group(), uiRoot = new Group();

    private Level level = new Level(Config.Text.LEVEL0);
    private Player player = new Player(120, 400);

    private World world = GameEnvironment.WORLD;

    final float SPEED = 10;
    float gravity = 8;

    @Override
    protected Parent createContent() {
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

        level.gameObjects.add(player);

        gameRoot.getChildren().addAll(level.physicsObjects);
        gameRoot.getChildren().addAll(level.gameObjects);


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

        uiRoot.getChildren().addAll(score, gBar, createInfo());

        Pane appRoot = new Pane();
        appRoot.getChildren().addAll(gameRoot, uiRoot);
        return appRoot;
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

        for (PhysicsGameObject obj : level.physicsObjects) {
            obj.onUpdate();
        }

        for (GameObject obj : level.gameObjects) {
            //            if (player != obj) {
            //                if (player.isColliding(obj)) {
            //                    System.out.println("Collision");
            //                }
            //            }

            obj.onUpdate();
        }

        level.physicsObjects.removeIf(node -> !((GameObject)node).isAlive());
        level.gameObjects.removeIf(node -> !((GameObject)node).isAlive());

        gameRoot.getChildren().removeIf(node -> !((GameObject)node).isAlive());
        debug.setText("Body count: " + world.getBodyCount());
        //debug.setText(player.body.getLinearVelocity() + " " + player.body.isAwake() + " " + player.body.getMass());
    }

    @Override
    protected void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE:
                Bullet b = new Bullet((float)player.getTranslateX() + 40, (float)player.getTranslateY(), Direction.RIGHT);
                level.physicsObjects.add(b);
                gameRoot.getChildren().add(b);
                break;
        }

        if (keys[PULL] && player.power.get() >= skillCosts[PULL]) {
            player.power.set(player.power.get() - skillCosts[PULL]);
            for (PhysicsGameObject obj : level.physicsObjects) {
                if (obj instanceof Enemy) {
                    ((Enemy)obj).setUnstable();
                }
                obj.body.applyForceToCenter((obj.body.getPosition().sub(player.body.getPosition()).mul(-30)));
            }
        }

        if (keys[PUSH] && player.power.get() >= skillCosts[PUSH]) {
            player.power.set(player.power.get() - skillCosts[PUSH]);
            for (PhysicsGameObject obj : level.physicsObjects) {
                if (obj instanceof Enemy) {
                    ((Enemy)obj).setUnstable();
                }
                obj.body.applyForceToCenter((obj.body.getPosition().sub(player.body.getPosition()).mul(30)));
            }
        }
    }

    @Override
    protected void handleKeyReleased(KeyEvent event) {
        // TODO Auto-generated method stub

    }
}
