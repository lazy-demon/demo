package com.example.ui;

import java.io.FileNotFoundException;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.example.scoreboard.Score;

import org.jetbrains.annotations.NotNull;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

class StartMenu extends FXGLMenu {

  GlobalMenuTemplate globalMenuTemplate;
  private UiComponentsFactory uiComponentsFactory = new UiComponentsFactory();
  private BorderPane primaryView;
  
  public StartMenu(@NotNull MenuType type) throws FileNotFoundException {
    super(type);

    globalMenuTemplate = new GlobalMenuTemplate(setMainMenu());
    primaryView = globalMenuTemplate.getMenu();

    getContentRoot().getChildren().addAll(primaryView);
  }

  public Button[] setMainMenu() {
    Button[] buttons = new Button[3];

    buttons[0] = this.uiComponentsFactory.customMainButton("Enter User");
    buttons[1] = this.uiComponentsFactory.customMainButton("Controls");
    buttons[2] = this.uiComponentsFactory.customMainButton("Settings");

    buttons[0].setOnMouseClicked(event -> {
      getContentRoot().getChildren().clear();
      getContentRoot().getChildren().addAll(setUser());
    });

    setBackground("/assets/start menu/bg-menu.jpg");

    return buttons;
  }

  public BorderPane setUser() {

    BorderPane borderPane = new BorderPane();

    GridPane gridPane = new GridPane();

    Image image;
    ImageView imageView;

    image = new Image("/assets/start menu/Kings and Pigs.png");

    imageView = new ImageView(image); 
    imageView.setFitWidth(500); 
    imageView.setFitHeight(110); 

    Label label = new Label("User Name");
    label.setFont(new Font("verdana", 30));

    TextField userNameField = new TextField();
    userNameField.setFont(new Font("verdana", 20));
    userNameField.setPadding(new Insets(10,10,10,10));
    userNameField.setPromptText("Enter Name...");

    Button startGameBtn = this.uiComponentsFactory.customMainButton("START GAME");

    startGameBtn.setOnMouseClicked(event -> {

      if ("".equals(userNameField.getText())) {
        userNameField.setPromptText("Required Enter Name...");

      } else {

        Score score = new Score(userNameField.getText());
        score.setUserScoreBoard();

        fireNewGame();
        getContentRoot().getChildren().clear();
        setBackground("/assets/start menu/bg-menu.jpg");
        getContentRoot().getChildren().addAll(primaryView);
      }
    });
    
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(60);

    gridPane.add(imageView, 0, 0);
    gridPane.add(label, 0, 1);
    gridPane.add(userNameField, 0, 2);
    gridPane.add(startGameBtn, 0, 3);

    GridPane.setHalignment(startGameBtn, HPos.CENTER);

    borderPane.setMinWidth(960);
    borderPane.setMinHeight(640);
    borderPane.setCenter(gridPane);

    setBackground("/assets/start menu/bg-user-menu.jpg");

    return borderPane;
  }

  public void setBackground(String path) {
    BackgroundImage menuBackground = new BackgroundImage(new Image(path, 960, 640, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    getContentRoot().setBackground(new Background(menuBackground));

  }
}
    