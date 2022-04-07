package com.example.ui;

import javafx.scene.control.Button;

public class UiComponentsFactory {
    
    public Button customMainButton(String text) {
        Button button = new Button(text);

        button.getStylesheets().add(getClass().getResource("/assets/start menu/style.css").toExternalForm());

        return button;
    }
}
