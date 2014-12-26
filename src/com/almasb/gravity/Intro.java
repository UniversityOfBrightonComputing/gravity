package com.almasb.gravity;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Intro extends Parent {

    private SequentialTransition timeline;

    // TODO: something more interesting
    public Intro() {
        Region bg = new Region();
        bg.setPrefSize(Config.APP_W, Config.APP_H);
        bg.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Text text = new Text("ALMAS");
        text.setFont(Font.font(72));
        text.setFill(Color.WHITE);
        text.setTranslateX(Config.APP_W / 2);
        text.setTranslateY(Config.APP_H / 2);

        Text text2 = new Text("ABSOFT");
        text2.setFont(Font.font(72));
        text2.setFill(Color.WHITE);
        text2.setTranslateX(Config.APP_W / 2);
        text2.setTranslateY(Config.APP_H / 2);

        getChildren().addAll(bg, text, text2);


        TranslateTransition tt = new TranslateTransition(Duration.seconds(3), text);
        tt.setToX(550);
        tt.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition tt2 = new TranslateTransition(Duration.seconds(1), text);
        tt2.setToX(570);

        TranslateTransition tt3 = new TranslateTransition(Duration.seconds(3), text);
        tt3.setToX(450);
        tt3.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition tt4 = new TranslateTransition(Duration.seconds(1), text);
        tt4.setToX(460);

        TranslateTransition tt5 = new TranslateTransition(Duration.seconds(3), text);
        tt5.setToX(410);
        tt5.setInterpolator(Interpolator.EASE_OUT);


        timeline = new SequentialTransition(tt, tt2, tt3, tt4, tt5);
        timeline.play();
        text2.setText(" BSOFT");
    }

    public void setOnFinished(EventHandler<ActionEvent> handler) {
        timeline.setOnFinished(handler);
    }
}
