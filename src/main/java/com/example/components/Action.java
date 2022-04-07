package com.example.components;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * UNDER CONSTRUCTION
 *
 */
public class Action {
    private Action() {
    }
    
    public static void moveLeft(Player playerd, KeyCode key) {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                playerd.left();
            }

            @Override
            protected void onActionEnd() {
                playerd.stop();
            }
        }, key);
    }
    
}
