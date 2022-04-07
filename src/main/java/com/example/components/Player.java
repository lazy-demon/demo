package com.example.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class Player extends Component {

    private int speed = 0;
    int jumps = 2;
    boolean isLookingRight = true;
    boolean isBusy = false;

    private PhysicsComponent physics;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, animJump, animFall, animAttack, animDoorIn, animDown, animHit;

    public Player() {
        animIdle = new AnimationChannel(FXGL.image("idle.png"), 11, 78, 58, Duration.seconds(1.1), 0, 10);
        animWalk = new AnimationChannel(FXGL.image("run.png"), 8, 78, 58, Duration.seconds(0.8), 0, 7);
        animJump = new AnimationChannel(FXGL.image("jump.png"), 1, 78, 58, Duration.seconds(0.1), 0, 0);
        animFall = new AnimationChannel(FXGL.image("fall.png"), 1, 78, 58, Duration.seconds(0.1), 0, 0);
        animAttack = new AnimationChannel(FXGL.image("attack.png"), 3, 78, 58, Duration.seconds(0.5), 0, 2);
        animDoorIn = new AnimationChannel(FXGL.image("doorIn.png"), 8, 78, 58, Duration.seconds(1), 0, 7);
        animDown = new AnimationChannel(FXGL.image("down.png"), 1, 78, 58, Duration.seconds(0.1), 0, 0);
        animHit = new AnimationChannel(FXGL.image("hit.png"), 2, 78, 58, Duration.seconds(0.5), 0, 1);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(20, 29));
        entity.getViewComponent().addChild(texture);

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps = 2;
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (isBusy == false) {
            if (physics.getVelocityY() > 0) {
                if (texture.getAnimationChannel() != animJump) {
                    texture.loopAnimationChannel(animJump);
                }
            } else if (physics.getVelocityY() < 0) {
                if (texture.getAnimationChannel() != animFall) {
                    texture.loopAnimationChannel(animFall);
                }
            } else if (physics.isMovingX()) {
                if (texture.getAnimationChannel() != animWalk) {
                    texture.loopAnimationChannel(animWalk);
                }
            } else {
                if (texture.getAnimationChannel() != animIdle) {
                    texture.loopAnimationChannel(animIdle);
                }
            }
        }
    }

    public void left() {
        getEntity().setScaleX(-1);
        physics.setVelocityX(-170);
        isLookingRight = false;
    }

    public void right() {
        getEntity().setScaleX(1);
        physics.setVelocityX(170);
        isLookingRight = true;
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    public void down() {
        if (isBusy == false) {
            isBusy = true;
            texture.playAnimationChannel(animDown);
            FXGL.runOnce(() -> {
                isBusy = false;
            }, Duration.seconds(1));
        }
    }

    public void attack() {
        if (isBusy == false) {
            isBusy = true;
            texture.playAnimationChannel(animAttack);
            FXGL.runOnce(() -> {
                isBusy = false;
            }, Duration.seconds(0.5));
        }
    }

    public void enter() {
        if (isBusy == false) {
            isBusy = true;
            texture.playAnimationChannel(animDoorIn);
            FXGL.runOnce(() -> {
                isBusy = false;
            }, Duration.seconds(1));
        }
    }

    public void hit() {
        if (isBusy == false) {
            isBusy = true;
            texture.playAnimationChannel(animHit);
            FXGL.runOnce(() -> {
                isBusy = false;
            }, Duration.seconds(0.5));
        }
    }

    public void jump() {
        if (jumps == 0)
            return;

        physics.setVelocityY(-300);

        jumps--;
    }

}