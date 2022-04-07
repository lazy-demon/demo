package com.example.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.Scene;

import org.jetbrains.annotations.NotNull;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

class StartMenu extends FXGLMenu {

  private UiComponentsFactory uiComponentsFactory = new UiComponentsFactory();
  
  public StartMenu(@NotNull MenuType type) throws FileNotFoundException {
    super(type);

    Button[] buttons = new Button[3];

    buttons[0] = this.uiComponentsFactory.customMainButton("New Game");
    buttons[1] = this.uiComponentsFactory.customMainButton("Controls");
    buttons[2] = this.uiComponentsFactory.customMainButton("Settins");

    buttons[0].setOnMouseClicked(event -> {
      fireNewGame();
    });

    GlobalMenuTemplate globalMenuTemplate = new GlobalMenuTemplate(type, buttons);

    BackgroundImage menuBackground = new BackgroundImage(new Image("/assets/start menu/bg-menu.jpg", 960, 640, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    getContentRoot().setBackground(new Background(new BackgroundImage[] { menuBackground }));

    getContentRoot().getChildren().addAll(globalMenuTemplate.getMenu());
  }
}
    