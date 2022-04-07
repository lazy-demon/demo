package com.example.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

import org.jetbrains.annotations.NotNull;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

class StartMenu extends FXGLMenu {

  private GridPane selectionBox;
  
  public StartMenu(@NotNull MenuType type) {
    super(type);

    Font font = new Font(23);

    Button buttonStart = new Button("New Game");
    buttonStart.setFont(font);

    buttonStart.setOnMouseClicked(event -> {
      fireNewGame();
    });

    Button buttonOptions = new Button("Options");
    buttonStart.setFont(font);

    Button buttonSettings = new Button("Settings");
    buttonStart.setFont(font);

    GridPane gridPane = new GridPane();

    gridPane.add(buttonStart, 0, 0);
    gridPane.add(buttonOptions, 1, 0);
    gridPane.add(buttonSettings, 2, 0);

    this.selectionBox = gridPane;

    getContentRoot().getChildren().addAll(buttonStart, buttonOptions, buttonSettings);
    
  }
}
    