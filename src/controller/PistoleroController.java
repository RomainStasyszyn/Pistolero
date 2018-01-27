package controller;

// Class that controls the pistolero's actions

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.*;
import view.Arenas;
import view.WildArena;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class PistoleroController {

    private Arenas arena;
    private Media media;
    private MediaPlayer media_player;

    public PistoleroController(Arenas arena) {
        this.arena = arena;
    }

    public void pistoleroCheckingMove(int xTranslation, int yTranslation) {
        int x = arena.getPistolero().getX();
        int y = arena.getPistolero().getY();
        // Vérifie si pistolero n'est pas déjà sur les bords de la map
        if(((x + xTranslation) > 0 || (x + xTranslation) < arena.getPrefArenaColumns()) && ((y + yTranslation) > 0 || (y + yTranslation) < arena.getPrefArenaRows())) {
            // Vérifie s'il y a un pilier sur la futur case
            if(arena.getMapArena().getElement(x + xTranslation, y + yTranslation) instanceof Obstacle) {
                // Vérifie si le pilier est déplacable
                if(((Obstacle) arena.getMapArena().getElement(x + xTranslation, y + yTranslation)).getMovable()) {
                    // Vérifie que le pilier ne soit pas lui-même sur un des bords
                    if(((x + xTranslation*2) >= 0 && (x + xTranslation*2) < arena.getPrefArenaColumns()) && ((y + yTranslation*2) >= 0 && (y + yTranslation*2) < arena.getPrefArenaRows())) {
                        // Vérifie que la futur case pour le pilier soit vide sinon on peut pas déplacer
                        if(arena.getMapArena().getElement(x + xTranslation*2, y + yTranslation*2) instanceof Uninhabited) {
                            pillarMove(xTranslation, yTranslation,(Obstacle) arena.getMapArena().getElement(xTranslation + x, yTranslation + y));
                            pistoleroMove(xTranslation, yTranslation);
                        }
                    }
                }
            }
            else if(arena.getMapArena().getElement(x + xTranslation, y + yTranslation) instanceof Creatures) {
                pistoleroBadMove(xTranslation, yTranslation, (Creatures) arena.getMapArena().getElement(x + xTranslation, y + yTranslation));
            }
            else {
                pistoleroMove(xTranslation, yTranslation);
            }
        }
    }

    public void pillarMove(int x, int y, Obstacle obstacle) {
        int last_x = obstacle.getX();
        int last_y = obstacle.getY();
        arena.getMapArena().leftCase(last_x, last_y);
        obstacle.setX(last_x+x);
        obstacle.setY(last_y+y);
        arena.getMapArena().moveOnCase(obstacle);
        for(int i = MapGrid.getMaxObstacles() ; i > 0 ; i--) {
            if((arena.getMapArena().getXObstacles(i) == last_x) && (arena.getMapArena().getYObstacles(i) == last_y)) {
                arena.getMapArena().setObstaclesPositions(i-1, obstacle.getX(), obstacle.getY());
            }
        }
        playStoneGrind();
        arena.imageViewObstacleMoving(x, y, last_x, last_y);
        //System.out.println("nouveau X : " + obstacle.getX() + " nouveau Y : " + obstacle.getY());
    }

    public void pistoleroBadMove(int x, int y, Creatures creature) {
        arena.imageViewPistoleroBadMoving(x, y);
        arena.getPistolero().setLife(arena.getPistolero().getLife().intValue() - creature.getBiteDamage());
    }

    public void pistoleroMove(int x, int y) {
        arena.getMapArena().leftCase(arena.getPistolero().getX(), arena.getPistolero().getY());
        arena.getPistolero().setX(arena.getPistolero().getX() + x);
        arena.getPistolero().setY(arena.getPistolero().getY() + y);
        arena.getMapArena().moveOnCase(arena.getPistolero());
        arena.imageViewPistoleroMoving(x, y);
        System.out.println("nouveau X : " + arena.getPistolero().getX() + " nouveau Y : " + arena.getPistolero().getY());
        System.out.println(arena.getMapArena().getElement(arena.getPistolero().getX(),arena.getPistolero().getY()));
    }

    public void switchWeapon(Weapons new_weapon) {
        Weapons temporary_weapon = arena.getPistolero().getPrimaryWeapon();
        arena.getPistolero().setPrimaryWeapon(arena.getPistolero().getSecondary_weapon());
        arena.getPistolero().setSecondaryWeapon(temporary_weapon);
    }

    // When we are reloading we cannot attacks ennemies anymore for two seconds
    public void reloadWeapon() {
        Timer timer = new Timer();
        Colt.playReloadSound();
        timer.schedule(new FinishReload(), 2000);
    }

    private class FinishReload extends TimerTask {
        public void run() {
            arena.getPistolero().getPrimaryWeapon().setBullets(arena.getPistolero().getPrimaryWeapon().getMagazine());
            arena.getPistolero().setAttack(true);
        }
    }

    // Charge à chaque fois pas fou
    public void playStoneGrind() {
        String path = new File("res/sound/pillar_move_sound.mp3").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        media_player = new MediaPlayer(media);
        media_player.setAutoPlay(true);
        media_player.setVolume(.4);
    }

    public static void playDefeatSound() {
        String path = new File("res/sound/game_over_sound.mp3").getAbsolutePath();
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer media_player = new MediaPlayer(media);
        media_player.setAutoPlay(true);
        media_player.setVolume(.4);
    }

    public static void playHurtingSound() {
        String path = new File("res/sound/pistolero_hurt_sound.mp3").getAbsolutePath();
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer media_player = new MediaPlayer(media);
        media_player.setAutoPlay(true);
        media_player.setVolume(.4);
    }
}
