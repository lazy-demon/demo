package com.example.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class Coin extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animIdle;


    public Coin() {
        animIdle = new AnimationChannel(FXGL.image("coin.png"), 4, 16, 16, Duration.seconds(0.75), 0, 3);
        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(8, 8));
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(animIdle);
    }
}
