package com.example.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;

public class UISceneFactory extends SceneFactory {
    
  public FXGLMenu newMainMenu() {
    return new StartMenu(MenuType.MAIN_MENU);
  }
  
  public FXGLMenu newGameMenu() {
    return new GameMenu(MenuType.GAME_MENU);
  }
}
