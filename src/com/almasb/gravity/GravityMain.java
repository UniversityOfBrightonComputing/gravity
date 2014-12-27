package com.almasb.gravity;

import javafx.application.Application;

public class GravityMain {
    public static void main(String[] args) {
        Config.init();
        Application.launch(App.class, args);
    }
}
