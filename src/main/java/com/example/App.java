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
import com.almasb.fxgl.texture.Texture;
import com.example.components.Door;
import com.example.components.Player;

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
        settings.setWidth(960);
        settings.setHeight(640);
     }

    private Entity player;
    private Entity door;

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).left();
                System.out.println("Beweegt naar links");
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
                System.out.println("Beweegt naar rechts");
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
                System.out.println("Beweegt naar omhoog");
            }
        }, KeyCode.W, VirtualButton.A);

        getInput().addAction(new UserAction("enter") {
            @Override
            protected void onActionBegin() {
                door.getComponent(Door.class).enter();
                System.out.println("Opent deur");
            }
        }, KeyCode.E, VirtualButton.X);

    }  

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new Factory());
        
        setLevelFromMap("map5.tmx");

        // ergens hier zit het probleem 
        player = spawn("player", 100, 300);
        door = spawn("door", 800, 521);
        set("player", player);
        set("door", door);


        // Viewport viewport = getGameScene().getViewport();
        // viewport.setBounds(-1500, 0, 250 * 70, getAppHeight());
        // viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        // viewport.setLazy(true);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 760);
    }

    public static void main(String[] args) {
        launch(args);
    }
}