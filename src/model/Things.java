package model;

// Class that represents all the things on the map

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;

public abstract class Things extends Observable {

    //Actual position of the thing on the X axe of the grid
    private int posX;
    // Actual position of the thing on the Y axe of the grid
    private int posY;
    // The sprite associates to the thing
    private Image sprite;

    public int getX() { return posX; }

    public void setX(int new_x) {
        posX = new_x;
        setChanged();
        notifyObservers();
    }

    public int getY() { return posY; }

    public void setY(int new_y) {
        posY = new_y;
        setChanged();
        notifyObservers();
    }

    public Image getImage() { return sprite; }

    public void setSprite(String new_path) throws Exception{
        try {
            InputStream input_stream = Files.newInputStream(Paths.get(new_path));
            sprite = new Image(input_stream);
            input_stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPathSprite() { return ""; }
}
