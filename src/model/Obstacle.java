package model;

// Class that represents an obstacle on the map that could be moving or not

import javafx.scene.image.Image;


public class Obstacle extends Things {

    private static final String UNMOVABLE_PATH_SPRITE = "res/img/unmovable_pillar_sprite.png";
    private static final String MOVABLE_PATH_SPRITE = "res/img/movable_pillar_sprite.png";
    private boolean movable;

    public Obstacle(int posX, int posY) throws Exception {
        setX(posX);
        setY(posY);
        shuffleMovable();
    }

    public String getPathSprite() { return this.movable == false ? UNMOVABLE_PATH_SPRITE : MOVABLE_PATH_SPRITE; }

    public boolean getMovable() { return movable; }

    public void setMovable(boolean new_movable) { movable = new_movable; }

    // Make a choice between make the obstacle movable or not
    public void shuffleMovable() throws Exception {
        int choice = (int)(Math.random() * 10) + 1;
        if(choice < 5) {
            setMovable(false);
            setSprite(UNMOVABLE_PATH_SPRITE);
        }
        else {
            setMovable(true);
            setSprite(MOVABLE_PATH_SPRITE);
        }
    }
}
