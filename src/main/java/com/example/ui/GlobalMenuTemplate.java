package com.example.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import org.jetbrains.annotations.NotNull;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GlobalMenuTemplate extends FXGLMenu {

    private BorderPane pane;

    public GlobalMenuTemplate(@NotNull MenuType type, Button[] buttons) {
        super(type);

        this.pane = new BorderPane();

        GridPane gridPane = new GridPane();

        Image image;
        ImageView imageView;

        image = new Image("/assets/start menu/Kings and Pigs.png");

        imageView = new ImageView(image); 
        imageView.setFitWidth(500); 
        imageView.setFitHeight(200); 

        gridPane.add(imageView, 1, 0);
        gridPane.add(buttons[0], 0, 1);
        gridPane.add(buttons[1], 1, 1);
        gridPane.add(buttons[2], 2, 1);

        this.pane.setMinWidth(FXGL.getAppWidth());
        this.pane.setMinHeight(FXGL.getAppHeight());
        this.pane.setCenter((Node)gridPane);

        gridPane.setAlignment(Pos.CENTER);
    }

    public BorderPane getMenu() {
        return this.pane;
    }
    
}
