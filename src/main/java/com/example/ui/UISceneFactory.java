package com.example.ui;

import java.io.FileNotFoundException;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;

public class UISceneFactory extends SceneFactory {

  public FXGLMenu newMainMenu() {
    try {
        return new StartMenu(MenuType.MAIN_MENU);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return null;
  }
  
  public FXGLMenu newGameMenu() {
    return new GameMenu(MenuType.GAME_MENU);
  }
}
