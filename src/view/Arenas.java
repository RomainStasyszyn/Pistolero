package view;

// Abstract class that represents the pattern of the arenas

import controller.ArenasController;
import controller.PistoleroController;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import model.MapGrid;
import model.Pistolero;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class Arenas extends Parent {

    private static final int PREF_ARENA_WIDTH = 1400;
    private static final int PREF_ARENA_HEIGHT = 900;
    private static final int PREF_ARENA_COLUMNS = 14;
    private static final int PREF_ARENA_ROWS = 9;
    private static final int PREF_ARENA_BOARD_WIDTH = 300;
    private static final int PREF_ARENA_BOARD_HEIGHT = 900;
    private static final int PREF_PISTOLERO_WIDTH = 100;
    private static final int PREF_PISTOLERO_HEIGHT = 100;
    private final BooleanProperty topPressed = new SimpleBooleanProperty(false);
    private final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
    private final BooleanProperty botPressed = new SimpleBooleanProperty(false);
    private final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
    private final BooleanBinding topAndRightPressed = topPressed.and(rightPressed);
    private final BooleanBinding botAndRightPressed = botPressed.and(rightPressed);
    private final BooleanBinding topAndLeftPressed = topPressed.and(leftPressed);
    private final BooleanBinding botAndLeftPressed = botPressed.and(leftPressed);
    protected Stage arena_stage;
    protected BorderPane arena_pane;
    protected Scene arena_scene;
    protected ImageView image_view_arena;
    protected ContextMenu context_menu;
    protected Pistolero pistolero;
    protected ImageView image_view_pistolero;
    protected ImageView [] image_view_obstacles;
    protected ImageView [] image_view_vampires;
    protected MapGrid map_arena;
    protected MyArenaMenu menu;
    protected PistoleroController pistolero_controller;
    protected ArenasController arenas_controller;
    protected ArrayList<ImageView> balles = new ArrayList<>();
    protected static IntegerProperty vampires_tues = new SimpleIntegerProperty(0);

    public void imageViewPistoleroMoving(int move_x, int move_y) {
        Translate translation = new Translate(move_x * 100, move_y * 100);
        image_view_pistolero.getTransforms().add(translation);
    }

    public void imageViewPistoleroBadMoving(int move_x, int move_y) {
        Translate translation = new Translate(move_x * 100, move_y * 100);
        image_view_pistolero.getTransforms().add(translation);
        Translate translation_back = new Translate(-(move_x * 100), -(move_y * 100));
        image_view_pistolero.getTransforms().add(translation_back);
    }

    public void imageViewObstacleMoving(int move_x, int move_y, int last_x, int last_y) {
        for(int i = MapGrid.getMaxObstacles() ; i > 0 ; i--) {
            if((image_view_obstacles[i-1].getX() == last_x * 100) && (image_view_obstacles[i-1].getY() == last_y * 100)) {
                // Translate translation = new Translate(move_x * 100, move_y * 100);
                //image_view_obstacles[i-1].getTransforms().add(translation);
                image_view_obstacles[i-1].setX((last_x * 100) + (move_x * 100));
                image_view_obstacles[i-1].setY((last_y * 100) + (move_y * 100));
            }
        }
    }

    /*protected void addLostLifeListener() {
        getArenaScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if(k.getCode() == KeyCode.E) {
                    System.out.println("J'y suis");
                   pistolero.setLife(pistolero.getLife().intValue()-100);
                   System.out.println(pistolero.getLife());

                }
            }
        });
    }*/

    protected void addUsedBulletListener() {
        getArenaScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if(k.getCode() == KeyCode.ENTER && pistolero.getAttack()) {
                    //System.out.println("Je tire");
                    //pistolero.getPrimaryWeapon().playShotSound(pistolero.getPrimaryWeapon().getMediaPlayerShot());
                    try {
                        creationBalle();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    pistolero.getPrimaryWeapon().shoot();
                    System.out.println(pistolero.getPrimaryWeapon().getBullets());

                }
            }
        });
    }

    public void creationBalle() throws Exception {
        //Essai pour balle
       if(pistolero.getPrimaryWeapon().getBullets().intValue() > 0) {
           InputStream input_stream_essai = Files.newInputStream(Paths.get("res/img/bullet_sprite_2.png"));
           Image image_essai = new Image(input_stream_essai);
           input_stream_essai.close();
           ImageView image_view_essai = new ImageView(image_essai);
           image_view_essai.setFitHeight(25);
           image_view_essai.setFitWidth(25);
           image_view_essai.setRotate(-90);
           image_view_essai.setX(((pistolero.getX()-1) * 100));
           image_view_essai.setY((pistolero.getY() * 100));
           balles.add(image_view_essai);
           getArenaPane().getChildren().add(image_view_essai);
       }
    }

    protected void addReloadListener() {
        getArenaScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if(k.getCode() == KeyCode.R) {
                    //System.out.println("Je tire");
                    //pistolero.getPrimaryWeapon().playShotSound(pistolero.getPrimaryWeapon().getMediaPlayerShot());
                    pistolero_controller.reloadWeapon();
                    System.out.println(pistolero.getPrimaryWeapon().getBullets());

                }
            }
        });
    }
/*
    public void addLifeListener() {
        pistolero.getLife().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {

            }
        });
    }*/

    protected void addContextMenuListener() {
        getArenaScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if(k.getCode() == KeyCode.ESCAPE) {
                    double x = 700;
                    double y = 400;
                    context_menu.show(image_view_arena, x, y);
                    /*try {
                        System.in.read();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }
            }
        });
    }

    protected void addLeftListener() {
        getArenaScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if(k.getCode() == KeyCode.Q && pistolero.getX() > 0 && !botPressed.getValue() && !topPressed.getValue() && !rightPressed.getValue()) {
                    pistolero_controller.pistoleroCheckingMove(-1,0);
                }
            }
        });
    }

    protected void addRightListener() {
        getArenaScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if(k.getCode() == KeyCode.D && pistolero.getX() < getPrefArenaColumns()-1 && !botPressed.getValue() && !topPressed.getValue() && !leftPressed.getValue()) {
                    pistolero_controller.pistoleroCheckingMove(1, 0);
                }
            }
        });
    }

    protected void addTopListener() {
        getArenaScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if(k.getCode() == KeyCode.Z && pistolero.getY() > 0 && !leftPressed.getValue() && !rightPressed.getValue() && !botPressed.getValue()) {
                    pistolero_controller.pistoleroCheckingMove(0, -1);
                }
            }
        });
    }

    protected void addBotListener() {
        getArenaScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if(k.getCode() == KeyCode.S && pistolero.getY() < getPrefArenaRows()-1 && !leftPressed.getValue() && !rightPressed.getValue() && !topPressed.getValue()) {
                    pistolero_controller.pistoleroCheckingMove(0, 1);
                }
            }
        });
    }

    protected void addTopLeftListener() {
        topAndLeftPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean werePressed, Boolean arePressed) {
                //System.out.println("Space and right pressed together");
                if(pistolero.getX() > 0 && pistolero.getY()> 0 && topPressed.getValue() && leftPressed.getValue())
                    pistolero_controller.pistoleroCheckingMove(-1, -1);
            }
        });
    }

    protected void addTopRightListener() {
        topAndRightPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean werePressed, Boolean arePressed) {
                //System.out.println("Space and right pressed together");
                if(pistolero.getX() < getPrefArenaColumns()-1 && pistolero.getY()> 0 && topPressed.getValue() && rightPressed.getValue())
                    pistolero_controller.pistoleroCheckingMove(1, -1);
            }
        });
    }

    protected void addBotLeftListener() {
        botAndLeftPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean werePressed, Boolean arePressed) {
                //System.out.println("Space and right pressed together");
                if(pistolero.getX() > 0 && pistolero.getY() < getPrefArenaRows()-1 && botPressed.getValue() && leftPressed.getValue())
                    pistolero_controller.pistoleroCheckingMove(-1, 1);
            }
        });
    }

    protected void addBotRightListener() {
        botAndRightPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean werePressed, Boolean arePressed) {
                //System.out.println("Space and right pressed together");
                if(pistolero.getX() < getPrefArenaColumns()-1 && pistolero.getY() < getPrefArenaRows()-1 && botPressed.getValue() && rightPressed.getValue())
                    pistolero_controller.pistoleroCheckingMove(1, 1);
            }
        });
    }

    protected void addKeyPressedEvents() {
        getArenaScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.Z) {
                    topPressed.set(true);
                } else if (ke.getCode() == KeyCode.D) {
                    rightPressed.set(true);
                }
                else if (ke.getCode() == KeyCode.Q) {
                    leftPressed.set(true);
                }
                else if (ke.getCode() == KeyCode.S) {
                    botPressed.set(true);
                }
            }
        });
    }

    protected void addKeyReleasedEvents() {
        getArenaScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.Z) {
                    topPressed.set(false);
                } else if (ke.getCode() == KeyCode.D) {
                    rightPressed.set(false);
                }
                else if (ke.getCode() == KeyCode.Q) {
                    leftPressed.set(false);
                }
                else if (ke.getCode() == KeyCode.S) {
                    botPressed.set(false);
                }
            }
        });
    }

    public Pistolero getArenaPistolero() { return pistolero; }

    public Stage getArenaStage() { return arena_stage; }

    public Pistolero getPistolero() { return pistolero; }

    public ArenasController getArenasController() { return arenas_controller; }

    public PistoleroController getPistoleroController() { return pistolero_controller; }

    public MapGrid getMapArena() { return map_arena; }

    public Pane getArenaPane() { return arena_pane; }

    public Scene getArenaScene() { return arena_scene; }

    public int getPrefArenaColumns() { return PREF_ARENA_COLUMNS; }

    public int getPrefArenaRows() { return PREF_ARENA_ROWS; }

    public int getPrefArenaWidth() { return PREF_ARENA_WIDTH; }

    public int getPrefArenaHeight() { return PREF_ARENA_HEIGHT; }

    public int getPrefArenaBoardWidth() { return PREF_ARENA_BOARD_WIDTH; }

    public int getPrefArenaBoardHeight() { return PREF_ARENA_BOARD_HEIGHT; }

    public int getPrefPistoleroWidth() { return PREF_PISTOLERO_WIDTH; }

    public int getPrefPistoleroHeighth() { return PREF_PISTOLERO_HEIGHT; }
}
