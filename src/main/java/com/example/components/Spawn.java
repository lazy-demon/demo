package com.example.components;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.example.EntityType.*;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Spawn {
    private Spawn() {
    }

    public static void diamond(int x, int y) {
        entityBuilder()
                .type(DIAMOND)
                .at(x, y)
                .viewWithBBox(new Circle(9, 7, 20, null))
                .with(new CollidableComponent(true))
                .with(new Diamond())
                .buildAndAttach();
    }

    public static void coin(int x, int y) {
        entityBuilder()
                .type(COIN)
                .at(x, y)
                .viewWithBBox(new Circle(9, 7, 5, null))
                .with(new CollidableComponent(true))
                .with(new Coin())
                .buildAndAttach();
    }

    public static void health(int x, int y) {
        AnimationChannel healthAnim = new AnimationChannel(FXGL.image("heartSmall.png"), 8, 18, 14,
                Duration.seconds(1), 0, 7);
        AnimatedTexture healthnode = new AnimatedTexture(healthAnim);
        healthnode.setTranslateX(x);
        healthnode.setTranslateY(y);
        healthnode.setScaleX(3);
        healthnode.setScaleY(3);
        getGameScene().addUINode(healthnode);
    }

    public static void healthbar(int x, int y) {
        AnimationChannel healthbarAnim = new AnimationChannel(FXGL.image("healthbar.png"), 1, 66, 34,
                Duration.seconds(1), 0, 0);
        AnimatedTexture healthbarnode = new AnimatedTexture(healthbarAnim);
        healthbarnode.setTranslateX(x);
        healthbarnode.setTranslateY(y);
        healthbarnode.setScaleX(3);
        healthbarnode.setScaleY(3);
        getGameScene().addUINode(healthbarnode);
    }

    // public static void door(Entity door, int x, int y) {
    //     door = spawn("door", x, y);
    //     set("door", door);
    // }
}