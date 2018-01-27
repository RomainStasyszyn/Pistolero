package model;

// Class that represents the pistolero, the hero of this game with his weapons to fight vampires

import controller.PistoleroController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import view.Arenas;
import view.WildArena;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Pistolero extends Characters {

    private static final int PREF_PISTOLERO_LIFE = 300;
    private static final int PREF_PISTOLERO_SPEED = 1;
    private static final int PREF_PISTOLERO_X = 13;
    private static final int PREF_PISTOLERO_Y = 4;
    private static final String PATH_SPRITE = "res/img/pistolero.gif";
    private Weapons primary_weapon;
    private Weapons secondary_weapon;
    private boolean attack = true;
    private boolean touchable;
    private Image sprite;
    private PistoleroController pistoleroController;

    public Pistolero(Arenas arena) throws Exception {
        setLife(PREF_PISTOLERO_LIFE);
        setSpeed(PREF_PISTOLERO_SPEED);
        setX(PREF_PISTOLERO_X);
        setY(PREF_PISTOLERO_Y);
        primary_weapon = new Colt();
        secondary_weapon = null;
        setAttack(true);
        pistoleroController = arena.getPistoleroController();
        setSprite(PATH_SPRITE);
    }

    public static int getPrefPistoleroX() { return PREF_PISTOLERO_X; }

    public static int getPrefPistoleroY() { return PREF_PISTOLERO_Y; }

    public static int getPrefPistoleroLife() { return PREF_PISTOLERO_LIFE; }

    public Weapons getPrimaryWeapon() { return primary_weapon; }

    public void setPrimaryWeapon(Weapons new_primary_weapon) { primary_weapon = new_primary_weapon; }

    public Weapons getSecondary_weapon() { return secondary_weapon; }

    public void setSecondaryWeapon(Weapons new_secondary_weapon) { primary_weapon = new_secondary_weapon; }

    public boolean getAttack() { return attack; }

    public void setAttack(boolean new_attack) { attack = new_attack; }

    public boolean getTouchable() { return touchable; }

    public void setTouchable(boolean newValue) { touchable = newValue; }
}
