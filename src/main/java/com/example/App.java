package com.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.action.ContinuousAction;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.util.Duration;

import java.util.EnumSet;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;

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

        Playmusic();



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

    public void Playmusic(){
//    Media media = new Media("file:///pirates.wav");
//    MediaPlayer player = new MediaPlayer(media);
//    player.setAutoPlay(true);



        FXGL.getSettings().setGlobalMusicVolume(1);
        Music muziek = FXGL.getAssetLoader().loadMusic("pirates.wav");
        FXGL.getAudioPlayer().loopMusic(muziek);
    }

    public void drip(){
        FXGL.onKey(KeyCode.X, () -> {
            FXGL.getAudioPlayer().pauseAllMusic();
            Music dripMuziek = FXGL.getAssetLoader().loadMusic("pirates.wav");
            FXGL.getAudioPlayer().loopMusic(dripMuziek);
        });
    }




    @Override
    protected void onUpdate(double tpf) {
        // System.out.println(onDoor);
        getInput().addTriggerListener(new TriggerListener() {

            // DOWN
            // @Override
            // protected void onAction(Trigger trigger) {
            //    while (trigger == KeyCode.E) {
            //        player.getComponent(Player.class).attack();
            //    }
            // }
        

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
                Spawn.coin(420, 750);
                break;
            case 2:
                Spawn.diamond(820, 550);
                Spawn.coin(270, 300);
                Spawn.coin(170, 350);
                Spawn.coin(418,300);
                break;
            case 3:
                Spawn.diamond(800, 550);
                Spawn.coin(220, 550);
                Spawn.coin(400, 550);
                Spawn.coin(600, 550);
                break;
            case 4:
                Spawn.diamond(800, 90);
                Spawn.coin(520, 550);
                Spawn.coin(800, 550);
                Spawn.coin(100, 200);
                break;
            case 5:
                Spawn.diamond(850, 80);
                Spawn.coin(490, 360);
                Spawn.coin(200, 200);
                Spawn.coin(300, 300);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}