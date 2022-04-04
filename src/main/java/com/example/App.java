package com.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;

/**
 * Hello world!
 *
 */
public class App extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Basic Game App");
    }

    @Override
    protected void initGame() {
        FXGL.entityBuilder()
                .at(200, 200)
                .with(new AnimationComponent())
                .buildAndAttach();
    }

    // background


    public static void main(String[] args) {
        launch(args);
    }
}
