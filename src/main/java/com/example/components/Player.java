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

    private PhysicsComponent physics;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, animJump, animFall;


    public Player() {
        animIdle = new AnimationChannel(FXGL.image("idle.png"), 11, 78, 58, Duration.seconds(1.1), 0, 10);
        animWalk = new AnimationChannel(FXGL.image("run.png"), 8, 78, 58, Duration.seconds(0.8), 0, 7);
        animJump = new AnimationChannel(FXGL.image("jump.png"), 1, 78, 58, Duration.seconds(1), 0, 0);
        animFall = new AnimationChannel(FXGL.image("fall.png"), 1, 78, 58, Duration.seconds(1), 0, 0);


        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(20, 29));
        entity.getTransformComponent().setScaleX(2);
        entity.getTransformComponent().setScaleY(2);
        entity.getViewComponent().addChild(texture);

        physics.onGroundProperty().addListener((obs, old, isOnGround)
        -> {
           if (isOnGround) {
               jumps = 2;
           }
       });
    }

    @Override
    public void onUpdate(double tpf) {
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

    public void left() {
        getEntity().setScaleX(-2);
        physics.setVelocityX(-170);
    }

    public void right() {
        getEntity().setScaleX(2);
        physics.setVelocityX(170);
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    public void jump() {
        if (jumps == 0)
            return;

        physics.setVelocityY(-300);

        jumps--;
    }

}