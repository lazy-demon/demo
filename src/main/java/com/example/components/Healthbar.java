package com.example.components;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.awt.*;

public class Healthbar extends Component {
    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private AnimationChannel animIdle;


    public Healthbar() {
        animIdle = new AnimationChannel(FXGL.image("healthbar.png"), 8, 66, 34, Duration.seconds(1), 0, 0);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(9, 7));
        entity.getViewComponent().addChild(texture);

    }

    @Override
    public void onUpdate(double tpf) {

    }
}