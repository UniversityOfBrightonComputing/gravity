package com.almasb.gravity;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public abstract class GameEnvironment extends Application {
    /**
     * Physics simulation world
     */
    public static final World WORLD = new World(new Vec2(0, -10));

    /**
     * Elements in gameRoot are affected by camera control
     */
    public static final Group LEVEL_ROOT = new Group();

    /**
     * Elements in uiRoot are always on the screen and do not move
     */
    public static final Group UI_ROOT = new Group();

    /**
     * Stores all game objects of the current level
     */
    public static final ObservableList<GameObject> LEVEL_OBJECTS = FXCollections.<GameObject>observableArrayList();

    protected static ArrayList<GameObject> tmpList = new ArrayList<GameObject>();

    // static Player

    /**
     * Used to dynamically add objects to already
     * running game
     *
     * This registers the object for game updates,
     * adds to the game view
     *
     * @param obj
     *              object to add to game
     */
    public static void addObject(GameObject obj) {
        tmpList.add(obj);
        // add to view list
        //LEVEL_ROOT.getChildren().add(obj);

        // add to update list
        //LEVEL_OBJECTS.add(obj);

        // add to physics list
        //return WORLD.createBody(obj.bodyDef);
    }

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
    protected int[] skillCosts = new int[LAST];

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

        skillCosts[UP] = 2;
        skillCosts[GRAVITY] = 5;
        skillCosts[PULL] = 120;
        skillCosts[PUSH] = 120;

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
        primaryStage.setWidth(Config.APP_W + 6);
        primaryStage.setHeight(Config.APP_H - 11);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Gravity");
        primaryStage.show();
        timer.start();
    }
}
