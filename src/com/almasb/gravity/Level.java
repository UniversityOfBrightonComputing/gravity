package com.almasb.gravity;

import java.util.List;

import javafx.scene.Group;

public class Level extends Group {

    private static final int SPACE = 0,
            PLATFORM = 1,
            ENEMY = 2,
            PLAYER = 3;

    private int[][] grid = new int[32][18];

    public Level() {
        for (int i = 0; i < Config.APP_H / Config.BLOCK_SIZE; i++) {
            for (int j = 0; j < Config.APP_W / Config.BLOCK_SIZE; j++) {
                grid[j][i] = Math.random() > 0.98 ? PLATFORM : SPACE;
                grid[j][i] = Math.random() > 0.98 ? ENEMY : grid[j][i];
            }
        }

        parse();
    }

    public Level(List<String> lines) {
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
        //createSpikes();

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
                        getChildren().add(new Platform(x, y, w, 40));
                        w = 0;
                    }
                }

                switch (grid[j][i]) {
                    case ENEMY:
                        getChildren().add(new Enemy(j*40, i*40));
                        break;
                }
            }

            if (w != 0) {
                getChildren().add(new Platform(x, y, w, 40));
                w = 0;
            }
        }
    }

    private void createScreenBounds() {
        //        for (int i = 0; i < Config.APP_H / Config.BLOCK_SIZE; i++) {
        //            getChildren().add(new Platform(-40, i*40));
        //            getChildren().add(new Platform(Config.APP_W, i*40));
        //        }
        //
        //        for (int i = 0; i < Config.APP_W / Config.BLOCK_SIZE; i++) {
        //            getChildren().add(new Platform(i*40, -40));
        //            getChildren().add(new Platform(i*40, Config.APP_H - 40));
        //        }

        getChildren().add(new Platform(0, 0, Config.APP_W, Config.BLOCK_SIZE));
        getChildren().add(new Platform(0, Config.APP_H - 40 - 40, Config.APP_W, Config.BLOCK_SIZE));
        getChildren().add(new Platform(0, 40, Config.BLOCK_SIZE, Config.APP_H - 40 - 40));
        getChildren().add(new Platform(Config.APP_W - 40, 40, Config.BLOCK_SIZE, Config.APP_H - 40 - 40));
    }

    private void createSpikes() {
        for (int i = 0; i < Config.APP_W / Config.BLOCK_SIZE; i++) {
            //getChildren().add(new Spike(i*40, Config.APP_H - 40 - 40));
        }
    }
}
