package com.example.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class King extends Component {

    int jumps = 2;
    boolean isLookingRight = true;
    boolean isBusy = false;

    private PhysicsComponent physics;

    private AnimatedTexture texture;
    private AnimationChannel animIdle;
    private AnimationChannel animAttack;
    private AnimationChannel animDoorIn;
    private AnimationChannel animDown;
    private AnimationChannel animHit;

    public King() {
        animIdle = new AnimationChannel(FXGL.image("king.png"), 12, 38, 28, Duration.seconds(1), 0, 11);
        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(20, 29));
        entity.getViewComponent().addChild(texture);

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (Boolean.TRUE.equals(isOnGround)) {
                jumps = 2;
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (!isBusy && texture.getAnimationChannel() != animIdle) {
            texture.loopAnimationChannel(animIdle);
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
        if (!isBusy) {
            isBusy = true;
            texture.playAnimationChannel(animDown);
            FXGL.runOnce(() -> isBusy = false, Duration.seconds(1));
        }
    }

    public void attack() {
        if (!isBusy) {
            isBusy = true;
            texture.playAnimationChannel(animAttack);
            FXGL.runOnce(() -> isBusy = false, Duration.seconds(0.5));
        }
    }

    public void enter() {
        if (!isBusy) {
            isBusy = true;
            texture.playAnimationChannel(animDoorIn);
            FXGL.runOnce(() -> isBusy = false, Duration.seconds(1));
        }
    }

    public void hit() {
        if (!isBusy) {
            isBusy = true;
            texture.playAnimationChannel(animHit);
            FXGL.runOnce(() -> isBusy = false, Duration.seconds(0.5));
        }
    }

    public void jump() {
        if (jumps == 0)
            return;

        physics.setVelocityY(-300);

        jumps--;
    }

}