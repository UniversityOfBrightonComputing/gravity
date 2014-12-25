package com.almasb.gravity;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;

public class Level {

    private static final int SPACE = 0,
            PLATFORM = 1,
            ENEMY = 2,
            COIN = 3,
            POWERUP_HP = 4,
            POWERUP_G = 5;

    private int[][] grid;

    public final ObservableList<GameObject> gameObjects = FXCollections.<GameObject>observableArrayList();

    public Level(List<String> lines) {
        grid = new int[lines.get(0).length()][lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[j][i] = Integer.parseInt(String.valueOf(line.charAt(j)));
            }
        }

        parse();
    }

    private void parse() {
        createScreenBounds();
        createSpikes();

        for (int i = 0; i < Config.APP_H / Config.BLOCK_SIZE; i++) {
            int x = -1, y = -1, w = 0;

            for (int j = 0; j < Config.APP_W / Config.BLOCK_SIZE; j++) {
                if (grid[j][i] == PLATFORM) {
                    if (w == 0) {
                        x = j * 40;
                        y = i * 40;
                        w = 40;
                    }
                    else {
                        w += 40;
                    }
                }
                else {
                    if (w != 0) {
                        gameObjects.add(new Platform(x, y, w, 40));
                        w = 0;
                    }
                }

                switch (grid[j][i]) {
                    case ENEMY:
                        gameObjects.add(new Enemy(j*40, i*40));
                        break;
                    case COIN:
                        gameObjects.add(new Coin(j*40, i*40));
                        break;
                }
            }

            if (w != 0) {
                gameObjects.add(new Platform(x, y, w, 40));
                w = 0;
            }
        }
    }

    private void createScreenBounds() {
        gameObjects.add(new Platform(0, 0, grid.length * 40, Config.BLOCK_SIZE));
        gameObjects.add(new Platform(0, 40, Config.BLOCK_SIZE, Config.APP_H - 40 - 40));
        gameObjects.add(new Platform(Config.APP_W - 40, 40, Config.BLOCK_SIZE, Config.APP_H - 40 - 40));
    }

    private void createSpikes() {
        gameObjects.add(new Spike(40, Config.APP_H - 40 - 40, grid.length * 40 - 40 - 40, 40));
    }
}
