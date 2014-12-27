package com.almasb.gravity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

public final class Config {
    /**
     * No outside instances
     */
    private Config() {}

    /**
     * The config singleton for convenient resource
     * management since we don't need to use classloaders
     * and we can retain same code for loading resources
     * within IDE and .jar
     */
    private static final Config instance = new Config();

    /* APP CONSTANTS */

    public static final float SCALE = 1.0f;

    public static final int BLOCK_SIZE = (int)(40 * SCALE);

    public static final int APP_W = (int)(32 * BLOCK_SIZE);
    public static final int APP_H = (int)(18 * BLOCK_SIZE);

    public static final int APP_HALF_W = APP_W / 2;

    /**
     * How many frames equal to a second
     * i.e. FPS
     */
    public static final int SECOND = 60;
    public static final float TIME_STEP = 1.0f / SECOND;

    public static Font FONT = Font.font(18);

    /**
     * A relative path from source directory
     * to resources
     */
    public static final String RESOURCES_ROOT = "/res/";
    public static final String IMAGES_ROOT = RESOURCES_ROOT + "images/";
    public static final String AUDIO_ROOT = RESOURCES_ROOT + "audio/";
    public static final String LEVELS_ROOT = RESOURCES_ROOT + "levels/";
    public static final String FONTS_ROOT = RESOURCES_ROOT + "fonts/";

    /* GAMEPLAY CONSTANTS */
    public static final int SCORE_COIN = 100;
    public static final int SCORE_POWERUP = 500;

    public static final Vec2 DEFAULT_GRAVITY = new Vec2(0, -10);

    public static final int MAX_LEVELS = 2;

    /* USER PREFERENCES (MODIFIABLE) */
    // TODO: persistent storage
    public static final SimpleDoubleProperty volume = new SimpleDoubleProperty();

    public static double getVolume() {
        return volume.get();
    }

    public static void init() {
        try {
            Fonts.loadAll();
            Images.loadAll();
            Audio.loadAll();
            Text.loadAll();
        }
        catch (Exception e) {
            // shouldn't happen unless someone's tampering with the jar
            System.out.println("Couldn't load game resource: " + e.getMessage());
            System.out.println("Game will now exit");
            System.exit(0);
        }
    }

    public static final class Fonts {
        public static Font LOGO;

        private static Font loadFont(String path, double size) throws Exception {
            try (InputStream is = instance.getClass().getResourceAsStream(FONTS_ROOT + path)) {
                return Font.loadFont(is, size);
            }
        }

        private static void loadAll() throws Exception {
            LOGO = loadFont("spacebar.ttf", 72);
        }
    }

    public static final class Images {
        public static Image PLAYER;
        public static Image ENEMY;
        public static Image PLATFORM;
        public static Image EXPLOSION;
        public static Image SPIKE;
        public static Image COIN;
        public static Image STONE;
        public static Image POWERUP;

        private static Image loadImage(String path) throws Exception {
            try (InputStream is = instance.getClass().getResourceAsStream(IMAGES_ROOT + path)) {
                return new Image(is);
            }
        }

        private static void loadAll() throws Exception {
            PLAYER = loadImage("player1.png");
            ENEMY = loadImage("enemy3.png");
            PLATFORM = loadImage("platform.png");
            EXPLOSION = loadImage("explosion.png");
            SPIKE = loadImage("spike.png");
            COIN = loadImage("coin.png");
            STONE = loadImage("stone.png");
            POWERUP = loadImage("powerup.png");
        }
    }

    public static final class Audio {

        public static AudioClip EXPLOSION;
        public static AudioClip COIN;
        public static AudioClip POWERUP;

        private static AudioClip loadAudio(String path) throws Exception {
            return new AudioClip(instance.getClass().getResource(AUDIO_ROOT + path).toExternalForm());
        }

        private static void loadAll() throws Exception {
            EXPLOSION = loadAudio("explosion.wav");
            COIN = loadAudio("coin.wav");
            POWERUP = loadAudio("powerup.wav");
        }
    }

    public static final class Text {
        public static final ArrayList< List<String> > LEVEL_DATA = new ArrayList< List<String> >();

        //        public static List<String> LEVEL0;
        //        public static List<String> LEVEL1;

        private static List<String> loadText(String path) throws Exception {
            ArrayList<String> lines = new ArrayList<String>();

            try (InputStream is = instance.getClass().getResourceAsStream(path);
                    BufferedReader bf = new BufferedReader(new InputStreamReader(is))) {

                String line = "";
                while ((line = bf.readLine()) != null)
                    lines.add(line);

                return lines;
            }
        }

        private static void loadAll() throws Exception {
            for (int i = 0; i < MAX_LEVELS; i++) {
                LEVEL_DATA.add(loadText(LEVELS_ROOT + "level" + i + ".txt"));
            }
        }
    }
}
