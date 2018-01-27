package view;

// Class that represents the menu of the arena game mode

import controller.PistoleroController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class MyArenaMenu extends Parent {

    private static final int INITIAL_LIFE = 3;
    private static final int PREF_ARENA_MENU_WIDTH = 300;
    private static final int PREF_ARENA_MENU_HEIGHT = 900;
    private static final float PREF_ARENA_MENU_OPACITY = 1f;
    private static final double PREF_ARENA_MENU_VBOX_SPACING = 40;
    private HBox hbox_life, hbox_gun, hbox_bullet, hbox_female_vampire, hbox_male_vampire, hbox_horror, hbox_timer;
    private ImageView image_view_weapon, image_view_bullets, image_view_life_1, image_view_life_2, image_view_life_3;
    private ImageView image_view_bullet_1,image_view_bullet_2,image_view_bullet_3,image_view_bullet_4,image_view_bullet_5,image_view_bullet_6;
    private ImageView image_view_female, image_view_male;
    private ImageView image_view_gun, image_view_horror;
    private MyTimer arena_timer;

    public MyArenaMenu() throws IOException {
        Pane pane = new Pane();
        pane.setPrefSize(PREF_ARENA_MENU_WIDTH, PREF_ARENA_MENU_HEIGHT);

        Rectangle rectangle = new Rectangle(PREF_ARENA_MENU_WIDTH, PREF_ARENA_MENU_HEIGHT);
        rectangle.setFill(Color.BLACK);
        rectangle.setOpacity(PREF_ARENA_MENU_OPACITY);

        VBox vbox = new VBox(PREF_ARENA_MENU_VBOX_SPACING);
        vbox.setPrefSize(PREF_ARENA_MENU_WIDTH, PREF_ARENA_MENU_HEIGHT);
        vbox.setAlignment(Pos.CENTER);

        hbox_timer = new HBox();
        hbox_timer.setPrefSize(50, 50);
        hbox_timer.setAlignment(Pos.CENTER);
        arena_timer = new MyTimer();
        hbox_timer.getChildren().addAll(arena_timer);

        try {
            initializeLife();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeWeapon();
        initializeBullets();
        initializeFemaleVampire();
        initializeMaleVampire();
        initializeHorror();
        vbox.getChildren().addAll(hbox_timer, hbox_horror, hbox_male_vampire, hbox_female_vampire, hbox_life, hbox_gun, hbox_bullet);
        pane.getChildren().addAll(rectangle, vbox);
        this.getChildren().addAll(pane);
    }

    public void initializeLife() throws Exception {
        hbox_life = new HBox(10);
        hbox_life.setPrefSize(200, 50);
        hbox_life.setAlignment(Pos.CENTER);
        InputStream input_stream_life = Files.newInputStream(Paths.get("res/img/life_sprite.png"));
        Image image = new Image(input_stream_life);
        input_stream_life.close();
       /* InputStream input_stream_lost_life = Files.newInputStream(Paths.get("res/img/life_lost_sprite.png"));
        Image image_lost = new Image(input_stream_lost_life);
        input_stream_lost_life.close();*/
        /*if(life == 0) {
            System.out.println("Je suis à 0");
            image_view_life_1 = new ImageView(image_lost);
            image_view_life_2 = new ImageView(image_lost);
            image_view_life_3 = new ImageView(image_lost);
        }
        else if(life == 100) {
            System.out.println("Je suis à 100");
            image_view_life_1 = new ImageView(image);
            image_view_life_2 = new ImageView(image_lost);
            image_view_life_3 = new ImageView(image_lost);
        }
        else if(life == 200) {
            System.out.println("Je suis à 200");
            //image_view_life_1 = new ImageView(image);
            //image_view_life_2 = new ImageView(image);
            //image_view_life_3 = new ImageView(image_lost);
            image_view_life_1.setImage(image_lost);
            image_view_life_2.setImage(image_lost);
            image_view_life_3.setImage(image_lost);
            //hbox_life.getChildren().addAll(image_view_life_1, image_view_life_2, image_view_life_2);
            //return;
        }
        else {
            image_view_life_1 = new ImageView(image);
            image_view_life_2 = new ImageView(image);
            image_view_life_3 = new ImageView(image);
        }*/
        image_view_life_1 = new ImageView(image);
        image_view_life_2 = new ImageView(image);
        image_view_life_3 = new ImageView(image);
        image_view_life_1.setFitHeight(50);
        image_view_life_1.setFitWidth(50);
        image_view_life_2.setFitHeight(50);
        image_view_life_2.setFitWidth(50);
        image_view_life_3.setFitHeight(50);
        image_view_life_3.setFitWidth(50);
        hbox_life.getChildren().addAll(image_view_life_1, image_view_life_2, image_view_life_3);
    }

    public void changeLife(int life) throws Exception {
        InputStream input_stream_lost_life = Files.newInputStream(Paths.get("res/img/life_lost_sprite.png"));
        Image image_lost = new Image(input_stream_lost_life);
        input_stream_lost_life.close();
        if(life == 200) {
            image_view_life_3.setImage(image_lost);
            //hbox_life.getChildren().add(image_view_life_3);
        }
        else if(life == 100) {
            image_view_life_2.setImage(image_lost);
        }
        else if(life == 0) {
            image_view_life_1.setImage(image_lost);
            PistoleroController.playDefeatSound();
        }
    }

    public void changeBullets(int bullet) throws Exception {
        InputStream input_stream_bullet_used = Files.newInputStream(Paths.get("res/img/bullet_used_sprite_2.png"));
        Image bullet_used = new Image(input_stream_bullet_used);
        input_stream_bullet_used.close();
        switch(bullet) {
            case 0 :
                image_view_bullet_1.setImage(bullet_used);
                break;
            case 1 :
                image_view_bullet_2.setImage(bullet_used);
                break;
            case 2 :
                image_view_bullet_3.setImage(bullet_used);
                break;
            case 3 :
                image_view_bullet_4.setImage(bullet_used);
                break;
            case 4 :
                image_view_bullet_5.setImage(bullet_used);
                break;
            case 5 :
                image_view_bullet_6.setImage(bullet_used);
                break;
            default :
                InputStream input_stream_bullet = Files.newInputStream(Paths.get("res/img/bullet_sprite_2.png"));
                Image image_bullet = new Image(input_stream_bullet);
                input_stream_bullet.close();
                image_view_bullet_1.setImage(image_bullet);
                image_view_bullet_2.setImage(image_bullet);
                image_view_bullet_3.setImage(image_bullet);
                image_view_bullet_4.setImage(image_bullet);
                image_view_bullet_5.setImage(image_bullet);
                image_view_bullet_6.setImage(image_bullet);
                break;
        }
    }

    public void initializeWeapon() throws IOException {
        hbox_gun = new HBox(0);
        hbox_gun.setPrefSize(200, 100);
        hbox_gun.setAlignment(Pos.CENTER);
        InputStream input_stream_gun = Files.newInputStream(Paths.get("res/img/gun_sprite.png"));
        Image image_gun = new Image(input_stream_gun);
        input_stream_gun.close();
        image_view_gun = new ImageView(image_gun);
        image_view_gun.setFitHeight(100);
        image_view_gun.setFitWidth(200);
        hbox_gun.getChildren().addAll(image_view_gun);
    }

    public void initializeBullets() throws IOException {
        hbox_bullet = new HBox(0);
        hbox_bullet.setPrefSize(300, 50);
        hbox_bullet.setAlignment(Pos.CENTER);
        InputStream input_stream_bullet = Files.newInputStream(Paths.get("res/img/bullet_sprite_2.png"));
        Image image_bullet = new Image(input_stream_bullet);
        input_stream_bullet.close();
        image_view_bullet_1 = new ImageView(image_bullet);
        image_view_bullet_1.setFitHeight(50);
        image_view_bullet_1.setFitWidth(50);
        image_view_bullet_2 = new ImageView(image_bullet);
        image_view_bullet_2.setFitHeight(50);
        image_view_bullet_2.setFitWidth(50);
        image_view_bullet_3 = new ImageView(image_bullet);
        image_view_bullet_3.setFitHeight(50);
        image_view_bullet_3.setFitWidth(50);
        image_view_bullet_4 = new ImageView(image_bullet);
        image_view_bullet_4.setFitHeight(50);
        image_view_bullet_4.setFitWidth(50);
        image_view_bullet_5 = new ImageView(image_bullet);
        image_view_bullet_5.setFitHeight(50);
        image_view_bullet_5.setFitWidth(50);
        image_view_bullet_6 = new ImageView(image_bullet);
        image_view_bullet_6.setFitHeight(50);
        image_view_bullet_6.setFitWidth(50);
        hbox_bullet.getChildren().addAll(image_view_bullet_1,image_view_bullet_2,image_view_bullet_3,image_view_bullet_4,image_view_bullet_5,image_view_bullet_6);
    }

    public void initializeFemaleVampire() throws  IOException {
        hbox_female_vampire = new HBox(30);
        hbox_female_vampire.setPrefSize(300, 100);
        hbox_female_vampire.setAlignment(Pos.CENTER_LEFT);
        InputStream input_stream_female = Files.newInputStream(Paths.get("res/img/female_vampire.png"));
        Image image_female = new Image(input_stream_female);
        input_stream_female.close();
        image_view_female = new ImageView(image_female);
        image_view_female.setFitHeight(100);
        image_view_female.setFitWidth(100);
        Label nb_tues = new Label();
        nb_tues.textProperty().bind(Arenas.vampires_tues.asString());
        //Text text = new Text("100");
        //text.setStyle("-fx-font-size: 3em;");
        //text.setStroke(Color.WHITE);
        nb_tues.setStyle("-fx-font-size: 3em;");
        //nb_tues.setStroke(Color.WHITE);
        hbox_female_vampire.getChildren().addAll(image_view_female, nb_tues);
    }

    public void initializeMaleVampire() throws  IOException {
        hbox_male_vampire = new HBox(10);
        hbox_male_vampire.setPrefSize(100, 100);
        hbox_male_vampire.setAlignment(Pos.CENTER_LEFT);
        InputStream input_stream_male = Files.newInputStream(Paths.get("res/img/male_vampire.png"));
        Image image_male = new Image(input_stream_male);
        input_stream_male.close();
        image_view_male = new ImageView(image_male);
        image_view_male.setFitHeight(100);
        image_view_male.setFitWidth(100);
        hbox_male_vampire.getChildren().addAll(image_view_male);
    }

    public void initializeHorror() throws  IOException {
        hbox_horror = new HBox(10);
        hbox_horror.setPrefSize(150, 150);
        hbox_horror.setAlignment(Pos.CENTER_LEFT);
        InputStream input_stream_horror = Files.newInputStream(Paths.get("res/img/horror.png"));
        Image image_horror = new Image(input_stream_horror);
        input_stream_horror.close();
        image_view_horror = new ImageView(image_horror);
        image_view_horror.setFitHeight(150);
        image_view_horror.setFitWidth(150);
        hbox_horror.getChildren().addAll(image_view_horror);
    }
    public MyTimer getTimer() { return arena_timer; }
}
