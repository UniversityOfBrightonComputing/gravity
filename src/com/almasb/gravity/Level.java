package com.almasb.gravity;

import java.util.List;

import com.almasb.gravity.Powerup.PowerType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Level {

    private static final int SPACE = 0,
            PLATFORM = 1,
            ENEMY = 2,
            COIN = 3,
            POWERUP_HP = 4,
            POWERUP_G = 5,
            STONE = 6,
            PLAYER = 7;

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

    public int getWidth() {
        return grid.length * Config.BLOCK_SIZE;
    }

    private void parse() {
        createLevelBounds();

        for (int i = 0; i < grid[0].length; i++) {
            int x = -1, y = -1, w = 0;

            for (int j = 0; j < grid.length; j++) {
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
                    case STONE:
                        gameObjects.add(new Stone(j*40, i*40));
                        break;
                    case POWERUP_HP:
                        gameObjects.add(new Powerup(j*40, i*40, PowerType.HP));
                        break;
                    case POWERUP_G:
                        gameObjects.add(new Powerup(j*40, i*40, PowerType.GRAVITY));
                        break;
                    case PLAYER:
                        gameObjects.add(GameEnvironment.PLAYER);
                        GameEnvironment.PLAYER.setTranslateX(j*40);
                        GameEnvironment.PLAYER.setTranslateY(i*40);
                        break;
                }
            }

            if (w != 0) {
                gameObjects.add(new Platform(x, y, w, 40));
                w = 0;
            }
        }
    }

    private void createLevelBounds() {
        // top
        gameObjects.add(new Platform(0, 0, grid.length * 40, Config.BLOCK_SIZE));

        // left
        gameObjects.add(new Platform(0, 40, Config.BLOCK_SIZE, Config.APP_H - 40 - 40));

        // right
        gameObjects.add(new Platform(grid.length * 40 - 40, 40, Config.BLOCK_SIZE, Config.APP_H - 40 - 40));

        // bot
        gameObjects.add(new Spike(40, Config.APP_H - 40 - 40, grid.length * 40 - 40 - 40, 40));
    }
}
