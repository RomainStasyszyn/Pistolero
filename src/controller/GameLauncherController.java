package controller;

// Class that represents the controller of the game launcher to stop main theme etc

import javafx.stage.Stage;
import launcher.GameLauncher;

import java.io.IOException;

public class GameLauncherController {

    private static GameLauncher game_launcher;

    public GameLauncherController(GameLauncher game_launcher) { this.game_launcher = game_launcher; }

    public static void restartGameLauncher(Stage stage) {
        try {
            game_launcher.myStart(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        game_launcher.getMediaPlayer().stop();
    }
}
