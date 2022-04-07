package com.example;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.IntroScene;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class GameIntro extends IntroScene {
    private List<Animation<?>> animations = new ArrayList<>();
    private int index = 0;

    public void gameIntro() {
        Font font = new Font(23);

        Button buttonStart = new Button("New Game");
        buttonStart.setFont(font);

        Button buttonOptions = new Button("Options");
        buttonStart.setFont(font);

        Button buttonSettings = new Button("Settings");
        buttonStart.setFont(font);

        Group buttons = new Group(buttonStart, buttonOptions, buttonSettings);

        buttons.getChildren().forEach(c -> 
            animations.add(FXGL.animationBuilder()
            .duration(Duration.seconds(1.66))
            .delay(Duration.seconds(1.66 * index++))
            .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
            .scale(c)
            .from(new Point2D(0, 0))
            .to(new Point2D(1, 1))
            .build())
        );

        animations.get(animations.size() - 1).setOnFinished(this::finishIntro);

        getContentRoot().getChildren().addAll(
                new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight()),
                buttons
        );
    }

    @Override
    protected void onUpdate(double tpf) {
        animations.forEach(a -> a.onUpdate(tpf));
    }

    @Override
    public void startIntro() {
        animations.forEach(Animation::start);
    }
}