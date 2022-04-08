package com.example.ui;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.core.util.EmptyRunnable;
import com.almasb.fxgl.dsl.FXGL;

import org.jetbrains.annotations.NotNull;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class GameMenu extends FXGLMenu {
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

      this.animation = FXGL.animationBuilder().duration(Duration.seconds(0.66D)).interpolator(Interpolators.EXPONENTIAL.EASE_OUT()).scale(getContentRoot()).from(new Point2D(0.0D, 0.0D)).to(new Point2D(1.0D, 1.0D)).build();
      
    }
    @Override
    public void onCreate() {
      this.animation.setOnFinished(EmptyRunnable.INSTANCE);
      this.animation.stop();
      this.animation.start();
    }
    
    @Override
    protected void onUpdate(double tpf) {
      this.animation.onUpdate(tpf);
    }
  }