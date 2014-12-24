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
import javafx.scene.text.Text;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public class App extends GameEnvironment {

    /**
     * Objects that participate in physics simulation
     *
     * Separating objects allow less load on the physics engine
     * and checked lists remove the need for constant type check and cast
     */
    private ObservableList<PhysicsGameObject> physicsObjects = FXCollections.<PhysicsGameObject>observableArrayList();

    /**
     * Objects that are not affected by physics like coins, powerups, etc
     * We do our own fast collision detection on them
     */
    private ObservableList<GameObject> gameObjects = FXCollections.<GameObject>observableArrayList();

    /**
     * Elements in gameRoot are affected by camera control
     * Elements in uiRoot are always on the screen and do not move
     */
    private Group gameRoot = new Group(), uiRoot = new Group();


    private Pane root = new Pane();

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

                if (body1.getLinearVelocity().abs().x > 50 || body1.getLinearVelocity().abs().y > 50)
                    ((PhysicsGameObject)body1.getUserData()).onDeath();
                if (body2.getLinearVelocity().abs().x > 50 || body2.getLinearVelocity().abs().y > 50)
                    ((PhysicsGameObject)body2.getUserData()).onDeath();
            }

            @Override
            public void endContact(Contact contact) {}
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });

        //gameRoot.getChildren().addAll(physicsObjects);
        //gameRoot.getChildren().addAll(gameObjects);


        Level level = new Level(Config.Text.LEVEL0);

        root.getChildren().addAll(level.getChildren());
        root.getChildren().add(player);


        ProgressBar gBar = new ProgressBar();
        gBar.setTranslateX(50);
        gBar.setTranslateY(50);
        gBar.progressProperty().bind(player.powerProperty().divide(player.maxPowerProperty().multiply(1.0f)));

        Pane theRoot = new Pane();
        theRoot.getChildren().addAll(gBar, root, createInfo());

        return theRoot;
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
                new Text("E - Force 'push")
                );

        return vbox;
    }

    @Override
    protected void update() {
        if (keys[UP] && player.powerProperty().get() >= skillCosts[UP]) {
            player.powerProperty().set(player.powerProperty().get() - skillCosts[UP]);
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

        if (keys[GRAVITY] && player.powerProperty().get() >= skillCosts[GRAVITY]) {
            player.powerProperty().set(player.powerProperty().get() - skillCosts[GRAVITY]);
            world.setGravity(new Vec2(0, gravity));
        }
        else {
            world.setGravity(new Vec2(0, -10));
        }

        world.step(Config.TIME_STEP, 8, 3);

        for (Node n : root.getChildren()) {
            //((PhysicsGameObject)n).body.setAwake(true);
            ((PhysicsGameObject)n).onUpdate();
        }

        root.getChildren().removeIf(node -> !((GameObject)node).isAlive());

        debug.setText(player.body.getLinearVelocity() + " " + player.body.isAwake() + " " + player.body.getMass());
    }

    @Override
    protected void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {

        }

        if (keys[PULL] && player.powerProperty().get() >= skillCosts[PULL]) {
            player.powerProperty().set(player.powerProperty().get() - skillCosts[PULL]);
            for (Node n : root.getChildren()) {
                if (n instanceof Enemy) {
                    ((Enemy)n).setUnstable();
                }
                ((PhysicsGameObject)n).body.applyForceToCenter(((PhysicsGameObject)n).body.getPosition().sub(player.body.getPosition()).mul(-100));
            }
        }

        if (keys[PUSH] && player.powerProperty().get() >= skillCosts[PUSH]) {
            player.powerProperty().set(player.powerProperty().get() - skillCosts[PUSH]);
            for (Node n : root.getChildren()) {
                if (n instanceof Enemy) {
                    ((Enemy)n).setUnstable();
                }
                ((PhysicsGameObject)n).body.applyForceToCenter(((PhysicsGameObject)n).body.getPosition().sub(player.body.getPosition()).mul(100));
            }
        }
    }

    @Override
    protected void handleKeyReleased(KeyEvent event) {
        // TODO Auto-generated method stub

    }
}
