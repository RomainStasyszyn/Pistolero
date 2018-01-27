package controller;

// Class that controls the main menu of the game

import javafx.stage.Stage;
import view.MyMainMenu;
import view.TimedArena;
import view.WildArena;

public class MyMainMenuController {

    private MyMainMenu myMainMenu;

    public MyMainMenuController(MyMainMenu myMainMenu) {
        this.myMainMenu = myMainMenu;
    }

    public void MyButtonWildArenaClicked(Stage stage) {
        try {
            GameLauncherController.stopMusic();
            WildArena wild_arena = new WildArena(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void MyButtonTimedArenaClicked(Stage stage) {
        try {
            GameLauncherController.stopMusic();
            TimedArena timed_arena = new TimedArena(stage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void MyButtonExitClicked() { System.exit(0); }
}
