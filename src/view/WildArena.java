package view;

// Class that represents the arena mode without timer

import controller.ArenaMenuController;
import controller.ArenasController;
import controller.GameLauncherController;
import controller.PistoleroController;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import launcher.GameLauncher;
import model.*;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WildArena extends Arenas {

    public WildArena(Stage stage) throws Exception {
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
        image_view_vampires = new ImageView[MapGrid.getMaxVampires()];
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

        menu = new MyArenaMenu();
        HBox wild_arena_hbox = new HBox();
        wild_arena_hbox.getChildren().addAll(image_view_arena, menu);
        getArenaPane().getChildren().addAll(wild_arena_hbox, image_view_pistolero);

        for(int i = MapGrid.getMaxObstacles() ; i > 0 ; i--) {
            int x = map_arena.getXObstacles(i);
            int y = map_arena.getYObstacles(i);
            map_arena.getElement(x,y).setSprite(map_arena.getElement(x,y).getPathSprite());
            image_view_obstacles[i-1] = new ImageView(map_arena.getElement(x,y).getImage());
            image_view_obstacles[i-1].setFitHeight(100);
            image_view_obstacles[i-1].setFitWidth(100);
            image_view_obstacles[i-1].setX(map_arena.getXObstacles(i) * getPrefPistoleroWidth());
            image_view_obstacles[i-1].setY(map_arena.getYObstacles(i) * getPrefPistoleroHeighth());
            getArenaPane().getChildren().add(image_view_obstacles[i-1]);
        }

        for(int i = MapGrid.getMaxVampires() ; i > 0 ; i--) {
            int x = map_arena.getXVampires(i);
            int y = map_arena.getYVampires(i);
            map_arena.getElement(x,y).setSprite(map_arena.getElement(x,y).getPathSprite());
            image_view_vampires[i-1] = new ImageView(map_arena.getElement(x,y).getImage());
            image_view_vampires[i-1].setFitHeight(100);
            image_view_vampires[i-1].setFitWidth(100);
            image_view_vampires[i-1].setX(map_arena.getXVampires(i) * getPrefPistoleroWidth());
            image_view_vampires[i-1].setY(map_arena.getYVampires(i) * getPrefPistoleroHeighth());
            getArenaPane().getChildren().add(image_view_vampires[i-1]);
        }


        pistolero.getLife().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                //menu.getLifeState().setText("Life : " + String.valueOf(newValue));
                try {
                    //System.out.println("etape 2 "+ pistolero.getLife());
                    pistolero_controller.playHurtingSound();
                    menu.changeLife(newValue.intValue());
                    if(pistolero.getLife().getValue() == 0) {
                       // Timer timer = new Timer();
                        pistolero_controller.playDefeatSound();
                       // timer.schedule(new FinishGame(), 2000);
                        GameLauncherController.restartGameLauncher(arena_stage);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        pistolero.getPrimaryWeapon().getBullets().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                //menu.getLifeState().setText("Life : " + String.valueOf(newValue));
                try {
                    System.out.println("etape 2 "+ newValue.intValue());
                    menu.changeBullets(newValue.intValue());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });



        // Ici on a des boucles inutiles...
        /*for(int i = MapGrid.getMaxObstacles() ; i > 0 ; i--) {
            getArenaPane().getChildren().add(image_view_obstacles[i-1]);
        }
        for(int i = MapGrid.getMaxVampires() ; i > 0 ; i--) {
            getArenaPane().getChildren().add(image_view_vampires[i-1]);
        }*/

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

        //addLostLifeListener();
        addUsedBulletListener();
        addReloadListener();

        arena_stage.setScene(getArenaScene());
        arena_stage.setTitle("Pistolero the vampire hunter - Wild Arena");
        arena_stage.show();
        System.out.println(map_arena.getElement(Pistolero.getPrefPistoleroX(), Pistolero.getPrefPistoleroY()));

        // AnimationTimer pour les balles.
        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                //image_view_essai.setTranslateX(-100);
                //image_view_essai.setRotate(90);

                if (now - lastUpdate >= 100_000_000) {
                    ArrayList<ImageView> balles_remove = new ArrayList<>();
                    ArrayList<ImageView> vampire_sprite_remove = new ArrayList<>();
                    for (int i = 0; i < balles.size(); i++) {
                        balles.get(i).setX(balles.get(i).getX() - 100);
                        System.out.println("X balle : " + balles.get(i).getX() + "  Y balle : " + balles.get(i).getY());
                        for (int j = 0; j < image_view_vampires.length; j++) {
                            //System.out.println("X :" + balles.get(i).getX());
                            //System.out.println("Y :" + balles.get(i).getY());
                            if ((int) (image_view_vampires[j].getX() / 100) == ((int) balles.get(i).getX() / 100) && ((int) image_view_vampires[j].getY() / 100) == ((int) balles.get(i).getY() / 100)) {
                                getArenaPane().getChildren().remove(balles.get(i));
                                //balles.remove(i);
                                balles_remove.add(balles.get(i));
                                vampire_sprite_remove.add(image_view_vampires[j]);
                                map_arena.setUnhabitated(((int) (image_view_vampires[j].getX() / 100)), ((int) (image_view_vampires[j].getY() / 100)));
                                //System.out.println(map_arena.getElement(((int)(image_view_vampires[j].getX()/100)), ((int)(image_view_vampires[j].getY()/100))).getClass());
                                final int p = j;
                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), evt -> image_view_vampires[p].setVisible(false)),
                                        new KeyFrame(Duration.seconds(0.1), evt -> image_view_vampires[p].setVisible(true)));
                                timeline.setCycleCount(10);
                                timeline.play();
                                //image_view_vampires[j].setVisible(false);
                                //arena_pane.getChildren().remove(image_view_vampires[j]);
                                image_view_vampires[j].setX(-500);
                                image_view_vampires[j].setY(-500);
                                vampires_tues.setValue(vampires_tues.getValue() + 1);
                            }
                        }
                        if (balles.get(i).getX() < 0 || map_arena.getElement((int) balles.get(i).getX() / 100, (int) balles.get(i).getY() / 100) instanceof Obstacle) {
                            getArenaPane().getChildren().remove(balles.get(i));
                            //balles.remove(i);
                            balles_remove.add(balles.get(i));
                        }
                    }
                    for (int iter = 0; iter < balles_remove.size(); iter++) {
                        balles.remove(balles_remove.get(iter));
                    }
                    /*for(int iter = 0 ; iter < vampire_sprite_remove.size() ; iter++) {
                        arena_pane.getChildren().remove(vampire_sprite_remove.get(iter));
                        //vampire_sprite_remove.get(iter).setX(-500);
                        //vampire_sprite_remove.get(iter).setY(-500);
                    }*/
                    /*for(int j = 0 ; j < image_view_vampires.length ; j++) {
                        if(image_view_vampires[j].getX() == -500) {
                            arena_pane.getChildren().remove(image_view_vampires[j]);
                        }
                    }*/
                    lastUpdate = now;
                }
                /*else if(now - lastUpdate >= 500_000_000) {
                    System.out.println(menu.getTimer().timerValue());
                    if(menu.getTimer().timerValue() == 0) {
                        pistolero_controller.playDefeatSound();
                        GameLauncherController.restartGameLauncher(stage);
                    }
                    lastUpdate = now;
                }*/
               /*else if(now - lastUpdate >= 150_000_000) {
                    for(int i = 0 ; i < balles.size() ; i++) {
                        for(int j = 0 ; j < image_view_vampires.length ; j++) {
                            if(image_view_vampires[j].getX() == balles.get(i).getX()%100 && image_view_vampires[j].getY() == balles.get(i).getY()%100) {
                                getArenaPane().getChildren().remove(balles.get(i));
                                balles.remove(i);
                                map_arena.setUnhabitated((int)image_view_vampires[j].getX(), (int)image_view_vampires[j].getY());
                                final int p = j;
                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), evt -> image_view_vampires[p].setVisible(false)),
                                        new KeyFrame(Duration.seconds( 0.1), evt -> image_view_vampires[p].setVisible(true)));
                                timeline.setCycleCount(10);
                                timeline.play();
                                image_view_vampires[j].setVisible(false);
                            }
                        }
                    }
                    lastUpdate = now;
                }*/
                /*if(now - lastUpdate >= 200_000_000) {
                    for(int i = 0 ; i < image_view_vampires.length ; i++) {
                        //image_view_vampires[i].setX(image_view_vampires[i].getX() - 100);
                        //System.out.println("X vampire : " + image_view_vampires[i].getX() + "  Y balle : " + image_view_vampires[i].getY());
                        image_view_vampires[i].setVisible(false);
                        image_view_vampires[i].setVisible(true);
                    }
                    lastUpdate = now;
                }*/
                /*if(now - lastUpdate >= 200_000_000) {
                    for(int i = 0 ; i < image_view_vampires.length ; i++) {
                        //image_view_vampires[i].setX(image_view_vampires[i].getX() - 100);
                        //System.out.println("X vampire : " + image_view_vampires[i].getX() + "  Y balle : " + image_view_vampires[i].getY());
                        image_view_vampires[i].setVisible(false);
                        image_view_vampires[i].setVisible(true);
                    }
                    lastUpdate = now;
                }*/
                if(menu.getTimer().timerValue() == 0) {
                    pistolero_controller.playDefeatSound();
                    GameLauncherController.restartGameLauncher(stage);
                    this.stop();
                }
                else if(vampires_tues.getValue() == 20) {
                    GameLauncherController.restartGameLauncher(stage);
                    this.stop();
                }
            }
        }.start();
    }

    /*private class FinishGame extends TimerTask {
        public void run() {
            //arena.getPistolero().getPrimaryWeapon().setBullets(arena.getPistolero().getPrimaryWeapon().getMagazine());
            //arena.getPistolero().setAttack(true);
            GameLauncherController.restartGameLauncher(arena_stage);
        }
    }*/

    public WildArena getWildArena() { return this; }
}
