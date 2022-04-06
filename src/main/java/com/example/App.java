package com.example;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.IntroScene;
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
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import com.example.components.Diamond;
import com.example.components.Door;
import com.example.components.Healthbar;
import com.example.components.Player;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
        settings.setWidth(960);
        settings.setHeight(640);
    }

    private Entity player, door, healthbar, diamond;

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(Player.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(Player.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(Player.class).jump();
            }
        }, KeyCode.W, VirtualButton.A);

        getInput().addAction(new UserAction("enter") {
            @Override
            protected void onActionBegin() {
                door.getComponent(Door.class).toggle();
            }
        }, KeyCode.E, VirtualButton.X);

    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new Factory());
        
        setLevelFromMap("terrain.tmx");

        player = spawn("player", 500, 500);
        door = spawn("door", 800, 521);
        // healthbar = spawn("healthbar", 800, 300);

        set("player", player);
        set("door", door);
        // set("healthbar", healthbar);
    
        entityBuilder()
        .type(DIAMOND)
        .at(400, 500)
        .viewWithBBox(new Circle(9, 7, 5, null))
        .with(new CollidableComponent(true))
        .with(new Diamond())
        .buildAndAttach();

        // Viewport viewport = getGameScene().getViewport();
        // viewport.setZoom(2);
        // viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        // viewport.setBounds(0, 0, 960, 640);
        // viewport.setLazy(true);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 760);

        onCollisionBegin(PLAYER, DIAMOND, (pl, prompt) -> {
            prompt.getComponent(Diamond.class).collect();

            FXGL.runOnce(() -> {
                prompt.removeFromWorld();
                door.getComponent(Door.class).toggle();
            }, Duration.seconds(0.7));
        });        
    }

    public static void main(String[] args) {
        launch(args);
    }
}