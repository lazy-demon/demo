package com.example.ui;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.IntroScene;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.core.util.EmptyRunnable;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.box2d.collision.shapes.Shape;

import org.jetbrains.annotations.NotNull;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class GameMenu extends FXGLMenu {
    private static final int SIZE = 150;
    
    private Animation<?> animation;

    private UiComponentsFactory uiComponentsFactory = new UiComponentsFactory();
    
    public GameMenu(@NotNull MenuType type) {
      super(type);

      
      Button[] buttons = new Button[3];

      buttons[0] = this.uiComponentsFactory.customMainButton("Resume");
      buttons[1] = this.uiComponentsFactory.customMainButton("Exit");
      buttons[2] = this.uiComponentsFactory.customMainButton("Home");

      buttons[0].setOnMouseClicked(e -> fireResume());
      buttons[1].setOnMouseClicked(e -> FXGL.getGameController().exit());
      buttons[2].setOnMouseClicked(e -> FXGL.getGameController().gotoMainMenu());

      GlobalMenuTemplate globalMenuTemplate = new GlobalMenuTemplate(buttons);

      getContentRoot().getChildren().addAll(globalMenuTemplate.getMenu());

      this.animation = FXGL.animationBuilder().duration(Duration.seconds(0.66D)).interpolator(Interpolators.EXPONENTIAL.EASE_OUT()).scale(new Node[] { (Node)getContentRoot() }).from(new Point2D(0.0D, 0.0D)).to(new Point2D(1.0D, 1.0D)).build();
      
    }
    public void onCreate() {
      this.animation.setOnFinished((Runnable)EmptyRunnable.INSTANCE);
      this.animation.stop();
      this.animation.start();
    }
    
    protected void onUpdate(double tpf) {
      this.animation.onUpdate(tpf);
    }
  }