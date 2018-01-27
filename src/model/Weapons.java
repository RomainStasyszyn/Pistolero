package model;

// Abstract class that represents any weapon for the pistolero

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Observable;

public abstract class Weapons extends Observable {

    // Name of the weapon
    private String name;
    // Damage dealt by the weapon
    private int damage;
    // Maximal capacity of bullets in the magazine
    private int magazine;
    // Actual numbers of bullets in the magazine
    private IntegerProperty bullets;
    // The sound of a shot
    private Media media_shot;
    private MediaPlayer media_player_shot;

    public String getName() { return name; }

    public void setName(String new_name) { name = new_name; }

    public int getDamage() { return damage; }

    public void setDamage(int new_damage) { damage = new_damage; }

    public int getMagazine() { return magazine; }

    public void setMagazine(int new_magazine) { magazine = new_magazine; }

    public IntegerProperty getBullets() { return bullets; }

    public void initializeBullets(int nb_bullet) { bullets = new SimpleIntegerProperty(nb_bullet); }

    public void setBullets(int new_bullets) {
        bullets.setValue(new_bullets);
    }

    public void shoot() {
        if(this.getBullets().intValue() > 0) {
            this.playShotSound(this.media_player_shot);
            this.setBullets(this.getBullets().intValue()-1);
        }
        else {

        }
    }

    public void initializeShotSound(String sound_path) {
        String path = new File(sound_path).getAbsolutePath();
        media_shot = new Media(new File(path).toURI().toString());
        media_player_shot = new MediaPlayer(media_shot);
    }

    public void playShotSound(MediaPlayer media_player) {
        media_player_shot = new MediaPlayer(media_shot);
        media_player.setAutoPlay(true);
        media_player.setVolume(.4);
    }

    public MediaPlayer getMediaPlayerShot() { return this.media_player_shot; }
}
