package com.almasb.gravity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class Config {

    // 40x40 tiles, w 32 h 18
    public static final int BLOCK_SIZE = 40;

    public static final int APP_W = 32 * BLOCK_SIZE;
    public static final int APP_H = 18 * BLOCK_SIZE;

    public static final String RESOURCES_ROOT = "./res/";
    public static final String IMAGES_ROOT = RESOURCES_ROOT + "images/";

    public static final class Image {
        public static javafx.scene.image.Image PLAYER;
        public static javafx.scene.image.Image ENEMY;
        public static javafx.scene.image.Image PLATFORM;

        private static javafx.scene.image.Image loadImage(String path) throws Exception {
            InputStream is = Config.class.getClassLoader().getResourceAsStream(path);
            javafx.scene.image.Image img = new javafx.scene.image.Image(is);
            is.close();
            return img;
        }

        static {
            try {
                PLAYER = loadImage(IMAGES_ROOT + "player1.png");
                ENEMY = loadImage(IMAGES_ROOT + "enemy1.png");
                PLATFORM = loadImage(IMAGES_ROOT + "platform.png");
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

            InputStream is = Config.class.getClassLoader().getResourceAsStream(path);
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
