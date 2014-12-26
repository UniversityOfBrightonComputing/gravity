package com.almasb.gravity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.AudioClip;
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

    public static Font FONT = Font.font(18);

    public static final String RESOURCES_ROOT = "/res/";
    public static final String IMAGES_ROOT = RESOURCES_ROOT + "images/";
    public static final String AUDIO_ROOT = RESOURCES_ROOT + "audio/";

    /* GAMEPLAY CONSTANTS */
    public static final int SCORE_COIN = 100;
    public static final int SCORE_POWERUP = 500;

    /* USER PREFERENCES (MODIFIABLE) */
    // TODO: persistent storage
    private static float volume = 0.1f;

    public static float getVolume() {
        return volume;
    }

    public static void setVolume(float v) {
        volume = v;
    }

    public static final class Fonts {
        public static Font LOGO;

        private static Font loadFont(String path, double size) throws Exception {
            try (InputStream is = instance.getClass().getResourceAsStream(RESOURCES_ROOT + "fonts/" + path)) {
                return Font.loadFont(is, size);
            }
        }

        static {
            try {
                LOGO = loadFont("spacebar.ttf", 72);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }


    public static final class Image {
        public static javafx.scene.image.Image PLAYER;
        public static javafx.scene.image.Image ENEMY;
        public static javafx.scene.image.Image PLATFORM;
        public static javafx.scene.image.Image EXPLOSION;
        public static javafx.scene.image.Image SPIKE;
        public static javafx.scene.image.Image COIN;
        public static javafx.scene.image.Image STONE;
        public static javafx.scene.image.Image POWERUP;

        private static javafx.scene.image.Image loadImage(String path) throws Exception {
            InputStream is = instance.getClass().getResourceAsStream(IMAGES_ROOT + path);
            javafx.scene.image.Image img = new javafx.scene.image.Image(is);
            is.close();
            return img;
        }

        static {
            try {
                PLAYER = loadImage("player1.png");
                ENEMY = loadImage("enemy3.png");
                PLATFORM = loadImage("platform.png");
                EXPLOSION = loadImage("explosion.png");
                SPIKE = loadImage("spike.png");
                COIN = loadImage("coin.png");
                STONE = loadImage("stone.png");
                POWERUP = loadImage("powerup.png");
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static final class Audio {

        public static AudioClip EXPLOSION;
        public static AudioClip COIN;
        public static AudioClip POWERUP;

        private static AudioClip loadAudio(String path) throws Exception {
            AudioClip barNote = new AudioClip(instance.getClass().getResource(AUDIO_ROOT + path).toExternalForm());
            return barNote;
        }

        static {
            try {
                EXPLOSION = loadAudio("explosion.wav");
                COIN = loadAudio("coin.wav");
                POWERUP = loadAudio("powerup.wav");
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
                LEVEL0 = loadText(RESOURCES_ROOT + "levels/level1.txt");
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
}
