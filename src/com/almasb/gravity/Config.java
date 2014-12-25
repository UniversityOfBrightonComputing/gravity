package com.almasb.gravity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Font;

public final class Config {

    private Config() {

    }

    private static final Config instance = new Config();

    /* APP CONSTANTS */

    public static final int BLOCK_SIZE = 40;

    public static final int APP_W = 32 * BLOCK_SIZE;
    public static final int APP_H = 18 * BLOCK_SIZE;

    /**
     * How many frames equal to a second
     * i.e. FPS
     */
    public static final int SECOND = 60;
    public static final float TIME_STEP = 1.0f / SECOND;

    public static final Font FONT = Font.font(18);

    public static final String RESOURCES_ROOT = "/res/";
    public static final String IMAGES_ROOT = RESOURCES_ROOT + "images/";

    /* GAMEPLAY CONSTANTS */
    public static final int SCORE_COIN = 100;
    public static final int SCORE_POWERUP = 500;

    public static final class Image {
        public static javafx.scene.image.Image PLAYER;
        public static javafx.scene.image.Image ENEMY;
        public static javafx.scene.image.Image PLATFORM;
        public static javafx.scene.image.Image EXPLOSION;
        public static javafx.scene.image.Image SPIKE;
        public static javafx.scene.image.Image COIN;

        private static javafx.scene.image.Image loadImage(String path) throws Exception {
            InputStream is = instance.getClass().getResourceAsStream(path);
            javafx.scene.image.Image img = new javafx.scene.image.Image(is);
            is.close();
            return img;
        }

        static {
            try {
                PLAYER = loadImage(IMAGES_ROOT + "player1.png");
                ENEMY = loadImage(IMAGES_ROOT + "enemy3.png");
                PLATFORM = loadImage(IMAGES_ROOT + "platform.png");
                EXPLOSION = loadImage(IMAGES_ROOT + "explosion.png");
                SPIKE = loadImage(IMAGES_ROOT + "spike.png");
                COIN = loadImage(IMAGES_ROOT + "coin.png");
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static final class Text {
        public static List<String> LEVEL0;

        private static List<String> loadText(String path) throws Exception {
            ArrayList<String> lines = new ArrayList<String>();

            InputStream is = instance.getClass().getResourceAsStream(path);
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));

            String line = "";
            while ((line = bf.readLine()) != null)
                lines.add(line);

            bf.close();
            is.close();

            return lines;
        }

        static {
            try {
                LEVEL0 = loadText(RESOURCES_ROOT + "levels/level0.txt");
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
}
