package com.example.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class Diamond extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animIdle;
    private AnimationChannel animCollect;


    public Diamond() {
        animIdle = new AnimationChannel(FXGL.image("diamond.png"), 10, 18, 14, Duration.seconds(1), 0, 9);
        animCollect = new AnimationChannel(FXGL.image("collectdiamond.png"), 2, 18, 14, Duration.seconds(0.7), 0, 1);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(9, 7));
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(animIdle);
    }

    public void collect() {
        texture.playAnimationChannel(animCollect);
    }
}
