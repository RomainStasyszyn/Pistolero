package model;

// Abstract class that represents any character on the map

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class Characters extends Things {

    // Life of the character
    private IntegerProperty life = new SimpleIntegerProperty();
    /*private IntegerProperty life = new IntegerProperty() {
        @Override
        public void bind(ObservableValue<? extends Number> observable) {

        }

        @Override
        public void unbind() {

        }

        @Override
        public boolean isBound() {
            return false;
        }

        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public int get() {
            return 0;
        }

        @Override
        public void addListener(ChangeListener<? super Number> listener) {

        }

        @Override
        public void removeListener(ChangeListener<? super Number> listener) {

        }

        @Override
        public void addListener(InvalidationListener listener) {

        }

        @Override
        public void removeListener(InvalidationListener listener) {

        }

        @Override
        public void set(int value) {

        }
    };*/
    // Actual speed of the character
    private int speed;

    public IntegerProperty getLife() { return life; }

    public void setLife(int new_life) { life.setValue(new_life); }

    public int getSpeed() { return speed; }

    public void setSpeed(int new_speed) { speed = new_speed; }

    public int shufflePosition(int limit) { return ((int)(Math.random()*limit)+1); }
}
