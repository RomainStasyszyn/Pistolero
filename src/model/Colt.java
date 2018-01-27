package model;

// Class that represents the clot weapon, a pistol with six bullets and oneshots basics vampires

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Colt extends Weapons {

    private static final String COLT_NAME = "Colt";
    private static final int COLT_MAGAZINE = 6;
    private static final String COLT_SOUND_PATH = "res/sound/colt_shot_sound.mp3";

    public Colt() {
        setName(COLT_NAME);
        setDamage(100);
        setMagazine(COLT_MAGAZINE);
        initializeBullets(COLT_MAGAZINE);
        initializeShotSound(COLT_SOUND_PATH);
    }

    public static void playReloadSound() {
        String path = new File("res/sound/colt_reload_sound.mp3").getAbsolutePath();
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer media_player = new MediaPlayer(media);
        media_player.setAutoPlay(true);
        media_player.setVolume(.4);
    }

    public static String getColtName() { return COLT_NAME; }

    public static int getColtMagazine() { return COLT_MAGAZINE; }
}
