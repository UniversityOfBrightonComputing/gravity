package com.almasb.gravity;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class App extends GameEnvironment {

    private Pane root = new Pane();

    private Player player = new Player(120, 400);

    private World world = GameEnvironment.WORLD;

    final float SPEED = 30;
    float gravity = 8;

    @Override
    protected Parent createContent() {
        Level level = new Level(Config.Text.LEVEL0);

        root.getChildren().addAll(level.getChildren());
        root.getChildren().add(player);

        Pane theRoot = new Pane();
        theRoot.getChildren().addAll(root, createInfo());
        return theRoot;
    }

    private VBox createInfo() {
        VBox vbox = new VBox(10);
        vbox.setTranslateX(900);
        vbox.setTranslateY(200);

        vbox.getChildren().addAll(
                new Text("WASD - gravity defying movement"),
                new Text("G - alter gravity"),
                new Text("Q - Force 'pull'"),
                new Text("E - Force 'push"));

        return vbox;
    }

    @Override
    protected void update() {
        if (keys[UP]) {
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

        if (keys[GRAVITY]) {
            world.setGravity(new Vec2(0, gravity));
        }
        else {
            world.setGravity(new Vec2(0, -10));
        }

        // 12 steps at 60 Hz seems a good ratio for smoothness
        for (int i = 0; i < 12; i++) {
            world.step(1 / 60.0f, 8, 3);
        }

        for (Node n : root.getChildren()) {
            //((PhysicsGameObject)n).body.setAwake(true);
            ((PhysicsGameObject)n).onUpdate();
        }
    }

    @Override
    protected void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {

        }

        if (keys[PULL]) {
            for (Node n : root.getChildren()) {
                ((PhysicsGameObject)n).body.applyForceToCenter(((PhysicsGameObject)n).body.getPosition().sub(player.body.getPosition()).mul(-100));
            }
        }

        if (keys[PUSH]) {
            for (Node n : root.getChildren()) {
                ((PhysicsGameObject)n).body.applyForceToCenter(((PhysicsGameObject)n).body.getPosition().sub(player.body.getPosition()).mul(100));
            }
        }
    }

    @Override
    protected void handleKeyReleased(KeyEvent event) {
        // TODO Auto-generated method stub

    }
}
