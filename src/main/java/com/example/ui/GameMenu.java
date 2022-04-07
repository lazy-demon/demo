package com.example.ui;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.IntroScene;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.box2d.collision.shapes.Shape;

import org.jetbrains.annotations.NotNull;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
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
    
    public GameMenu(@NotNull MenuType type) {
      super(type);
      
    }
    public void onCreate() {
      
      this.animation.stop();
      this.animation.start();
    }
    
    protected void onUpdate(double tpf) {
      this.animation.onUpdate(tpf);
    }
  }