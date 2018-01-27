package launcher;// Class that launch the game

import controller.GameLauncherController;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import view.MyMainMenu;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameLauncher extends Application {

    private static final int PREF_WIDTH = 1700;
    private static final int PREF_HEIGHT = 900;
    private static Stage stage_game;
    private Media media;
    private MediaPlayer media_player;

    @Override
    public void start(Stage stage) throws IOException {
        myStart(stage);
    }

    public void myStart(Stage stage) throws IOException {
        stage_game = stage;
        Pane pane = new Pane();
        pane.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        String path = new File("res/sound/main_menu_sound.mp3").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        media_player = new MediaPlayer(media);

        InputStream input_stream_main_menu = Files.newInputStream(Paths.get("res/img/main_menu_background.png"));
        Image image_main_menu = new Image(input_stream_main_menu);
        input_stream_main_menu.close();
        ImageView image_view_main_menu = new ImageView(image_main_menu);
        image_view_main_menu.setFitHeight(PREF_HEIGHT);
        image_view_main_menu.setFitWidth(PREF_WIDTH);

        MyMainMenu main_menu = new MyMainMenu();
        main_menu.setVisible(true);
        pane.getChildren().addAll(image_view_main_menu, main_menu);

        GameLauncherController game_launcher_controller = new GameLauncherController(this);

        Scene scene = new Scene(pane);
        stage_game.setScene(scene);
        stage_game.setTitle("Pistolero the vampire hunter");
        stage_game.show();
        initializeMusic();
    }

    public void initializeMusic() {
        media_player.setAutoPlay(true);
        media_player.setCycleCount(MediaPlayer.INDEFINITE);
        media_player.setVolume(.2);
    }

    public static Stage getStage() { return stage_game; }

    public Media getMedia() { return media; }

    public MediaPlayer getMediaPlayer() { return media_player; }

    public static void main(String []args) {
        launch(args);
    }
}
