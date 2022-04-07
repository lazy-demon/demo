package com.example.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class King extends Component {
    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animHit;

    boolean isBusy = false;

    public King() {
        animIdle = new AnimationChannel(FXGL.image("king.png"), 12, 38, 28, Duration.seconds(1), 0, 11);
        animHit = new AnimationChannel(FXGL.image("kingHit.png"), 2, 38, 28, Duration.seconds(1), 0, 1);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(19, 14));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        // if (!isBusy) {
        //     System.out.println(isBusy);
        //     if (texture.getAnimationChannel() != animIdle) {
        //         texture.loopAnimationChannel(animIdle);
        //     }
    // }
    }}