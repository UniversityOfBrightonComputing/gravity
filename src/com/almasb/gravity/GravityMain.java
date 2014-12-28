package com.almasb.gravity;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GravityMain {
    public static void main(String[] args) {
        Config.init();
        Application.launch(App.class, args);
    }
}
