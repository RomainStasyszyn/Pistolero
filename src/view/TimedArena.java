package view;

// Class that represents a timed arena, an arena where we must kill all the ennemies before the end of the timer

import controller.ArenasController;
import controller.GameLauncherController;
import controller.PistoleroController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.MapGrid;
import model.Obstacle;
import model.Pistolero;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TimedArena extends Arenas {

    public TimedArena(Stage stage) throws Exception {
        arena_stage = stage;
        arena_pane = new BorderPane();
        arena_pane.setPrefSize(getPrefArenaWidth() + getPrefArenaBoardWidth(), getPrefArenaHeight());
        Pane arena_center_pane = new Pane();
        arena_center_pane.setPrefSize(getPrefArenaWidth(), getPrefArenaHeight());
        Pane arena_board_pane = new Pane();
        arena_board_pane.setPrefSize(getPrefArenaBoardWidth(), getPrefArenaHeight());
        arena_pane.setCenter(arena_center_pane);
        arena_pane.setRight(arena_board_pane);
        pistolero = new Pistolero(this);
        pistolero_controller = new PistoleroController(this);
        arenas_controller = new ArenasController(this);
        image_view_obstacles = new ImageView[MapGrid.getMaxObstacles()];
        map_arena = new MapGrid(pistolero, arenas_controller, getPrefArenaColumns(), getPrefArenaRows());
        arena_scene = new Scene(arena_pane);

        InputStream input_stream = Files.newInputStream(Paths.get("res/img/arena_cobblestone.jpg"));
        Image image = new Image(input_stream);
        input_stream.close();
        image_view_arena = new ImageView(image);
        image_view_arena.setFitHeight(getPrefArenaHeight());
        image_view_arena.setFitWidth(getPrefArenaWidth());

        image_view_pistolero = new ImageView(pistolero.getImage());
        image_view_pistolero.setFitHeight(getPrefPistoleroHeighth());
        image_view_pistolero.setFitWidth(getPrefPistoleroWidth());
        image_view_pistolero.setX(pistolero.getX() * getPrefPistoleroWidth());
        image_view_pistolero.setY(pistolero.getY() * getPrefPistoleroHeighth());

        for(int i = MapGrid.getMaxObstacles() ; i > 0 ; i--) {
            int x = map_arena.getXObstacles(i);
            int y = map_arena.getYObstacles(i);
            map_arena.getElement(x,y).setSprite(map_arena.getElement(x,y).getPathSprite());
            image_view_obstacles[i-1] = new ImageView(map_arena.getElement(x,y).getImage());
            image_view_obstacles[i-1].setFitHeight(100);
            image_view_obstacles[i-1].setFitWidth(100);
            image_view_obstacles[i-1].setX(map_arena.getXObstacles(i) * getPrefPistoleroWidth());
            image_view_obstacles[i-1].setY(map_arena.getYObstacles(i) * getPrefPistoleroHeighth());
        }

        menu = new MyArenaMenu();
        pistolero.getLife().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                //menu.getLifeState().setText("Life : " + String.valueOf(newValue));
            }
        });
        HBox wild_arena_hbox = new HBox();
        wild_arena_hbox.getChildren().addAll(image_view_arena, menu);
        getArenaPane().getChildren().addAll(wild_arena_hbox, image_view_pistolero);
        for(int i = MapGrid.getMaxObstacles() ; i > 0 ; i--) {
            getArenaPane().getChildren().add(image_view_obstacles[i-1]);
        }

        context_menu = new ContextMenu();
        //context_menu.setPrefSize(800, 500);
        context_menu.setStyle("-fx-font-size: 3em;");
        MenuItem menu_item_main_menu = new MenuItem("Return to main menu");
        MenuItem menu_item_exit = new MenuItem("Exit options menu");
        menu_item_main_menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                context_menu.hide();
                GameLauncherController.restartGameLauncher(arena_stage);
            }
        });
        menu_item_exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                context_menu.hide();
            }
        });
        context_menu.getItems().add(menu_item_main_menu);
        context_menu.getItems().add(menu_item_exit);

        // Listeners for the menu
        addContextMenuListener();

        // Listeners for the pistolero controls (north, south, east, west)
        addLeftListener();
        addRightListener();
        addTopListener();
        addBotListener();

        // Listeners for pistolero(northeast, northwest, southeast, southwest)
        addTopLeftListener();
        addTopRightListener();
        addBotLeftListener();
        addBotRightListener();

        addKeyPressedEvents();
        addKeyReleasedEvents();

        arena_stage.setScene(getArenaScene());
        arena_stage.setTitle("Pistolero the vampire hunter - Wild Arena");
        arena_stage.show();
    }

    public TimedArena getTimedArena() { return this; }
}
