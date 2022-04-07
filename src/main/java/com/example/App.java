package com.example;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
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
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.example.components.Diamond;
import com.example.components.Door;
import com.example.components.Healthbar;
import com.example.components.Player;
import com.example.ui.UISceneFactory;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
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
        settings.setAppIcon("fall.png");
        settings.setTitle("Kings and Pigs");
        settings.setVersion("0.1");
        settings.setWidth(960);
        settings.setHeight(640);
        settings.setMainMenuEnabled(true);
        settings.setDeveloperMenuEnabled(false);
        settings.setEnabledMenuItems(EnumSet.of(MenuItem.EXTRA));
        settings.setSceneFactory((SceneFactory)new UISceneFactory());
    }

    private Entity player, door, healthbar, diamond, king;

    boolean onDoor = false;

    int level = 0;

    // make list
   List<AnimatedTexture> list = new ArrayList<>();



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
                if (onDoor) {
                    player.getComponent(Player.class).enter();
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
                getGameScene().removeUINode(list.get(2));
                player.getComponent(Player.class).hit();
            }
        }, KeyCode.BACK_SLASH, VirtualButton.X);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new Factory());

        nextLevel();

        player = spawn("player", 100, 100);
        // king = spawn("king", 285, 228);
        door = spawn("door", 600, 521);

        player.setZIndex(1);
        door.setZIndex(0);

        set("player", player);
        // set("king", king);
        set("door", door);

        entityBuilder()
                .type(DIAMOND)
                .at(400, 500)
                .viewWithBBox(new Circle(9, 7, 5, null))
                .with(new CollidableComponent(true))
                .with(new Diamond())
                .buildAndAttach();

        AnimationChannel healthbarAnim = new AnimationChannel(FXGL.image("healthbar.png"), 1, 66, 34, Duration.seconds(1), 0, 0);
        AnimatedTexture healthbarnode = new AnimatedTexture(healthbarAnim);
        healthbarnode.setTranslateX(100);
        healthbarnode.setTranslateY(50);
        healthbarnode.setScaleX(3);
        healthbarnode.setScaleY(3);
        getGameScene().addUINode(healthbarnode);

        AnimationChannel health1Anim = new AnimationChannel(FXGL.image("heartSmall.png"), 8, 18, 14, Duration.seconds(1), 0, 7);
        AnimatedTexture health1node = new AnimatedTexture(health1Anim);
        health1node.setTranslateX(85);
        health1node.setTranslateY(60);
        health1node.setScaleX(3);
        health1node.setScaleY(3);
        getGameScene().addUINode(health1node);

        AnimationChannel health2Anim = new AnimationChannel(FXGL.image("heartSmall.png"), 8, 18, 14, Duration.seconds(1), 0, 7);
        AnimatedTexture health2node = new AnimatedTexture(health2Anim);
        health2node.setTranslateX(120);
        health2node.setTranslateY(60);
        health2node.setScaleX(3);
        health2node.setScaleY(3);
        getGameScene().addUINode(health2node);

        AnimationChannel health3Anim = new AnimationChannel(FXGL.image("heartSmall.png"), 8, 18, 14, Duration.seconds(1), 0, 7);
        AnimatedTexture health3node = new AnimatedTexture(health3Anim);
        health3node.setTranslateX(150);
        health3node.setTranslateY(60);
        health3node.setScaleX(3);
        health3node.setScaleY(3);
        getGameScene().addUINode(health3node);

        Viewport viewport = getGameScene().getViewport();
        viewport.setZoom(2);
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setBounds(0, 0, 960, 640);
        viewport.setLazy(true);

        list.add(health1node);
        list.add(health2node);
        list.add(health3node);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 760);

        onCollisionOneTimeOnly(PLAYER, DIAMOND, (pl, prompt) -> {
            prompt.getComponent(Diamond.class).collect();

            FXGL.runOnce(() -> {
                prompt.removeFromWorld();
                door.getComponent(Door.class).toggle();
            }, Duration.seconds(0.7));
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(PLAYER, DOOR) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                super.onCollisionBegin(a, b);
                onDoor = true;
                System.out.println("on door");
            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                super.onCollisionEnd(a, b);
                onDoor = false;
                System.out.println("not on door");
            }
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
        setLevelFromMap("map" + level  + ".tmx");
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
