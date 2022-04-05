package com.example.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class Door extends Component {
    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private AnimationChannel animOpen, animClose;


    public Door() {
        animOpen = new AnimationChannel(FXGL.image("open.png"), 5, 46, 56, Duration.seconds(1), 0, 4);
        animClose = new AnimationChannel(FXGL.image("close.png"), 3, 46, 56, Duration.seconds(1), 0, 2);


        texture = new AnimatedTexture(animOpen);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(23, 28));
        entity.getViewComponent().addChild(texture);

    }

    @Override
    public void onUpdate(double tpf) {

    }
}