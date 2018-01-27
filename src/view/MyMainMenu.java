package view;

// Class that represents a menu of the game

import controller.MyMainMenuController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import launcher.GameLauncher;


public class MyMainMenu extends Parent {

    private static final int PREF_MAIN_MENU_WIDTH = 300;
    private static final int PREF_MAIN_MENU_HEIGHT = 900;
    private static final float PREF_MAIN_MENU_OPACITY = 0.3f;
    private static final double PREF_MAIN_MENU_VBOX_SPACING = 40;
    private MyMainMenuController myMainMenuController;

    public MyMainMenu() {
        Pane pane = new Pane();
        pane.setPrefSize(PREF_MAIN_MENU_WIDTH, PREF_MAIN_MENU_HEIGHT);

        Rectangle rectangle = new Rectangle(PREF_MAIN_MENU_WIDTH, PREF_MAIN_MENU_HEIGHT);
        rectangle.setFill(Color.DARKRED);
        rectangle.setOpacity(PREF_MAIN_MENU_OPACITY);

        VBox vbox = new VBox(PREF_MAIN_MENU_VBOX_SPACING);
        vbox.setPrefSize(PREF_MAIN_MENU_WIDTH, PREF_MAIN_MENU_HEIGHT);
        vbox.setAlignment(Pos.CENTER);

        myMainMenuController = new MyMainMenuController(this);

        MyButton wild_arena = new MyButton("WILD ARENA");
        wild_arena.setOnMouseClicked(event -> {
            try {
                myMainMenuController.MyButtonWildArenaClicked(GameLauncher.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        MyButton timed_arena = new MyButton("TIMED ARENA");
        timed_arena.setOnMouseClicked(event -> {
            try {
                myMainMenuController.MyButtonTimedArenaClicked(GameLauncher.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        MyButton exit = new MyButton("EXIT");
        exit.setOnMouseClicked(event -> {
            myMainMenuController.MyButtonExitClicked();
        });

        vbox.getChildren().addAll(wild_arena, timed_arena, exit);
        pane.getChildren().addAll(rectangle, vbox);
        this.getChildren().addAll(pane);
    }
}
