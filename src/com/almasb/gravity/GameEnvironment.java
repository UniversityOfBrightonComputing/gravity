package com.almasb.gravity;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public abstract class GameEnvironment extends Application {

    public static final World WORLD = new World(new Vec2(0, -10));

    /**
     * User actions
     */
    protected static final int UP = 0,
            DOWN = 1,
            LEFT = 2,
            RIGHT = 3,
            GRAVITY = 4,
            PULL = 5,
            PUSH = 6,
            LAST = 7;

    // TODO: add observable binding -> methods are triggered automatically
    private HashMap<Integer, KeyCode> keyBindings = new HashMap<Integer, KeyCode>();
    // TODO: make enums
    protected boolean[] keys = new boolean[LAST];

    protected abstract Parent createContent();
    protected abstract void update();
    protected abstract void handleKeyPressed(KeyEvent event);
    protected abstract void handleKeyReleased(KeyEvent event);

    protected AnimationTimer timer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        keyBindings.put(UP, KeyCode.W);
        keyBindings.put(DOWN, KeyCode.S);
        keyBindings.put(RIGHT, KeyCode.D);
        keyBindings.put(LEFT, KeyCode.A);
        keyBindings.put(GRAVITY, KeyCode.G);
        keyBindings.put(PULL, KeyCode.Q);
        keyBindings.put(PUSH, KeyCode.E);

        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            for (Integer action : keyBindings.keySet()) {
                if (keyBindings.get(action) == event.getCode()) {
                    keys[action] = true;
                    break;
                }
            }

            handleKeyPressed(event);
        });
        scene.setOnKeyReleased(event -> {
            for (Integer action : keyBindings.keySet()) {
                if (keyBindings.get(action) == event.getCode()) {
                    keys[action] = false;
                    break;
                }
            }

            handleKeyReleased(event);
        });

        primaryStage.setScene(scene);
        primaryStage.setWidth(Config.APP_W);
        primaryStage.setHeight(Config.APP_H);
        primaryStage.setTitle("Gravity");
        primaryStage.show();
        timer.start();
    }
}
