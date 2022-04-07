package com.example.ui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GlobalMenuTemplate  {

    private BorderPane pane;

    public GlobalMenuTemplate(Button[] buttons) {

        this.pane = new BorderPane();

        GridPane gridPane = new GridPane();

        Image image;
        ImageView imageView;

        image = new Image("/assets/start menu/Kings and Pigs.png");

        imageView = new ImageView(image); 
        imageView.setFitWidth(500); 
        imageView.setFitHeight(110); 

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(60);

        gridPane.add(imageView, 1, 0);

        for (int i = 0; i < buttons.length; i++) {
            gridPane.add(buttons[i], i, 1);
            GridPane.setHalignment(buttons[i], HPos.CENTER);
        }

        this.pane.setMinWidth(960);
        this.pane.setMinHeight(640);
        this.pane.setCenter((Node)gridPane);
    }


    public BorderPane getMenu() {
        return this.pane;
    }
    
}
