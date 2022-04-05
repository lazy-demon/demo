package com.example;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.example.EntityType.*;

/**
 * Hello world!
 *
 */
public class App extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) { 
        settings.setTitle("Kings and Pigs");
        settings.setVersion("0.1");
        settings.setWidth(30 * 32);
        settings.setHeight(20 * 32);
     }

    private Entity player;

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveRight();
            }
        }, KeyCode.D);

        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveLeft();
            }
        }, KeyCode.A);
    }

    @Override
    protected void initGameVars(Map<String, Object> context) {

        
    }    

    @Override
    protected void initGame() {
        FXGL.setLevelFromMap("terrain.tmx");        

        player = FXGL.entityBuilder()
                .at( 480, 518)
                .with(new AnimationComponent())
                .buildAndAttach();

                FXGL.getGameWorld().addEntityFactory(new Factory());

                Viewport viewport = getGameScene().getViewport();
                viewport.setBounds(-1500, 0, 250 * 70, getAppHeight());
                viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
                viewport.setLazy(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}