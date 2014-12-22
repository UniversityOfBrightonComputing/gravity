package com.almasb.gravity;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class App extends Application {

    private enum UserAction {
        NONE, UP, DOWN, LEFT, RIGHT, GRAVITY
    }

    private Pane root = new Pane();

    private JBoxRectangle player = new JBoxRectangle(100, 500, 30, 30);

    private AnimationTimer timer;

    private Timeline timeline;

    private World world = new World(new Vec2(0, -10));

    final float SPEED = 30;
    float gravity = 8;

    private UserAction action = UserAction.NONE;

    private boolean[] keys = new boolean[5];

    private static final int UP = 0,
            DOWN = 1,
            LEFT = 2,
            RIGHT = 3,
            GRAVITY = 4;

    private Parent createContent() {
        // screen bounds

        JBoxRectangle bound = new JBoxRectangle(-20, 0, 20, Config.APP_H);
        bound.bodyDef.type = BodyType.STATIC;
        bound.body = world.createBody(bound.bodyDef);
        bound.body.createFixture(bound.fixtureDef);

        root.getChildren().add(bound);

        JBoxRectangle bound2 = new JBoxRectangle(Config.APP_W, 0, 20, Config.APP_H);
        bound2.bodyDef.type = BodyType.STATIC;
        bound2.body = world.createBody(bound2.bodyDef);
        bound2.body.createFixture(bound2.fixtureDef);

        root.getChildren().add(bound2);

        JBoxRectangle bound3 = new JBoxRectangle(0, -20, Config.APP_W, 20);
        bound3.bodyDef.type = BodyType.STATIC;
        bound3.body = world.createBody(bound3.bodyDef);
        bound3.body.createFixture(bound3.fixtureDef);

        root.getChildren().add(bound3);

        JBoxRectangle bound4 = new JBoxRectangle(0, Config.APP_H - 20, Config.APP_W, 20);
        bound4.bodyDef.type = BodyType.STATIC;
        bound4.body = world.createBody(bound4.bodyDef);
        bound4.body.createFixture(bound4.fixtureDef);

        root.getChildren().add(bound4);

        // platforms
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                JBoxRectangle platform = new JBoxRectangle(j * 600, (i+1) * 300, 450, 50);
                platform.bodyDef.type = BodyType.STATIC;
                platform.body = world.createBody(platform.bodyDef);
                platform.body.createFixture(platform.fixtureDef);

                root.getChildren().add(platform);
            }
        }

        // enemies
        for (int i = 0; i < 4; i++) {
            JBoxRectangle enemy = new JBoxRectangle(i*50 + 600, 450, 40, 40);
            enemy.rect.setFill(Color.RED);
            enemy.body = world.createBody(enemy.bodyDef);
            enemy.body.createFixture(enemy.fixtureDef);

            root.getChildren().add(enemy);
        }

        player.rect.setFill(Color.BLUE);
        player.bodyDef.fixedRotation = true;
        player.body = world.createBody(player.bodyDef);
        player.body.createFixture(player.fixtureDef);
        root.getChildren().add(player);


        //        timeline = new Timeline(new KeyFrame(Duration.seconds(1/60.0f), event -> {
        //            for (Node n : root.getChildren()) {
        //                world.step(1 / 60.0f, 8, 3);
        //                ((JBoxRectangle)n).update();
        //            }
        //        }));
        //
        //
        //        timeline.setCycleCount(Timeline.INDEFINITE);


        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
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

                for (Node n : root.getChildren()) {
                    ((JBoxRectangle)n).body.setAwake(true);

                    world.step(1 / 60.0f, 8, 3);
                    ((JBoxRectangle)n).update();
                }
            }
        };

        Pane theRoot = new Pane();
        theRoot.getChildren().addAll(root, createInfo());
        return theRoot;
    }

    private VBox createInfo() {
        VBox vbox = new VBox(10);
        vbox.setTranslateX(800);
        vbox.setTranslateY(100);

        vbox.getChildren().addAll(
                new Text("WASD - gravity defying movement"),
                new Text("G - alter gravity"),
                new Text("Q - Force 'pull'"),
                new Text("E - Force 'push"));

        return vbox;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    keys[UP] = true;
                    //action = UserAction.UP;
                    //player.body.setLinearVelocity(new Vec2(player.body.getLinearVelocity().x, SPEED));
                    //y -= 5;
                    break;
                case S:
                    keys[DOWN] = true;
                    //action = UserAction.DOWN;
                    //player.body.setLinearVelocity(new Vec2(player.body.getLinearVelocity().x, -SPEED));
                    //y += 5;
                    break;
                case A:
                    keys[LEFT] = true;
                    //action = UserAction.LEFT;
                    //player.body.setLinearVelocity(new Vec2(-SPEED, player.body.getLinearVelocity().y));
                    //x -= 5;
                    break;
                case D:
                    keys[RIGHT] = true;
                    //action = UserAction.RIGHT;
                    //player.body.setLinearVelocity(new Vec2(SPEED, player.body.getLinearVelocity().y));
                    //x += 5;
                    break;
                case SPACE:
                    break;
                case G:
                    keys[GRAVITY] = true;
                    //action = UserAction.GRAVITY;
                    break;
                case Q:
                    for (Node n : root.getChildren()) {
                        ((JBoxRectangle)n).body.applyForceToCenter(((JBoxRectangle)n).body.getPosition().sub(player.body.getPosition()).mul(-100));
                    }
                    break;
                case E:
                    for (Node n : root.getChildren()) {
                        ((JBoxRectangle)n).body.applyForceToCenter(((JBoxRectangle)n).body.getPosition().sub(player.body.getPosition()).mul(100));
                    }
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                    keys[UP] = false;
                    //player.body.setLinearVelocity(new Vec2(player.body.getLinearVelocity().x, 0));
                    //y -= 5;
                    break;
                case S:
                    keys[DOWN] = false;
                    //player.body.setLinearVelocity(new Vec2(player.body.getLinearVelocity().x, 0));
                    //y += 5;
                    break;
                case A:
                    keys[LEFT] = false;
                    //player.body.setLinearVelocity(new Vec2(0, player.body.getLinearVelocity().y));
                    //x -= 5;
                    break;
                case D:
                    keys[RIGHT] = false;
                    //player.body.setLinearVelocity(new Vec2(0, player.body.getLinearVelocity().y));
                    //x += 5;
                    break;
                case SPACE:
                    break;
                case G:
                    keys[GRAVITY] = false;
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setWidth(Config.APP_W);
        primaryStage.setHeight(Config.APP_H);
        primaryStage.setTitle("Gravity");
        primaryStage.show();
        timer.start();
        //timeline.play();
    }
}
