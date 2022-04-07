package com.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.example.components.Diamond;
import com.example.components.Door;
import com.example.components.Player;
import com.example.components.Spawn;
import com.example.ui.UISceneFactory;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.EnumSet;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.example.EntityType.*;

/**
 * Hello world!
 *
 */
public class App extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setAppIcon("fall.png");
        settings.setTitle("Kings and Pigs");
        settings.setVersion("0.1");
        settings.setWidth(960);
        settings.setHeight(640);
        settings.setMainMenuEnabled(true);
        settings.setDeveloperMenuEnabled(false);
        settings.setEnabledMenuItems(EnumSet.of(MenuItem.EXTRA));
        settings.setSceneFactory(new UISceneFactory());
    }

    private Entity player;
    private Entity door;

    boolean onDoor = false;
    boolean diamondCollected = false;

    int level = 0;

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
                if (onDoor && diamondCollected) {
                    player.getComponent(Player.class).enter();
                    FXGL.runOnce(App.this::nextLevel, Duration.seconds(0.7));
                } else {
                    player.getComponent(Player.class).jump();
                }
            }
        }, KeyCode.W, VirtualButton.A);

        getInput().addAction(new UserAction("attack") {
            @Override
            protected void onActionBegin() {
                player.getComponent(Player.class).attack();
            }
        }, KeyCode.E, VirtualButton.X);

        getInput().addAction(new UserAction("next level") {
            @Override
            protected void onActionBegin() {
                nextLevel();
            }
        }, KeyCode.ENTER, VirtualButton.X);

        getInput().addAction(new UserAction("hit") {
            @Override
            protected void onActionBegin() {
                // getGameScene().removeUINode(list.get(2));
                player.getComponent(Player.class).hit();
            }
        }, KeyCode.BACK_SLASH, VirtualButton.X);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new Factory());

        nextLevel();

        player = spawn("player", 300, 550);

        player.setZIndex(1);
        door.setZIndex(0);

        set("player", player);

            Spawn.healthbar(100, 50);
            Spawn.health(85, 60);
            Spawn.health(120, 60);
            Spawn.health(150, 60);

        Viewport viewport = getGameScene().getViewport();
        viewport.setZoom(2);
        viewport.bindToEntity(player, getAppWidth() / 2d, getAppHeight() / 2d);
        viewport.setBounds(0, 0, 960, 640);
        viewport.setLazy(true);


    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 760);

        onCollisionOneTimeOnly(PLAYER, DIAMOND, (pl, prompt) -> {
            prompt.getComponent(Diamond.class).collect();

            FXGL.runOnce(() -> {
                prompt.removeFromWorld();
                diamondCollected = true;
                door.getComponent(Door.class).toggle();
            }, Duration.seconds(0.7));
        });

        onCollisionOneTimeOnly(PLAYER, COIN, (pl, prompt) -> prompt.removeFromWorld());

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(PLAYER, DOOR) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                super.onCollisionBegin(a, b);
                onDoor = true;
            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                super.onCollisionEnd(a, b);
                onDoor = false;
            }
        });

    }

    private void nextLevel() {
        if (level != 0) {
            player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(100, 100));
        }
        if (level == 5) {
            level = 1;
        } else {
            level++;
        }

        setLevelFromMap("map" + level + ".tmx");

        diamondCollected = false;
        door = spawn("door", 600, 521);
        set("door", door);

        switch (level) {
            case 1:
                Spawn.diamond(400, 550);
                Spawn.coin(420, 550);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}