package controller;

// Class that controls the button of the game

import javafx.scene.paint.Color;
import view.MyButton;

public class MyButtonController {

    private MyButton myButton;

    public MyButtonController(MyButton myButton) {
        this.myButton = myButton;
    }

    public void MyButtonExcited() {
        myButton.getRectangle().setFill(Color.GREY);
    }

    public void MyButtonEntered() {
        myButton.getRectangle().setFill(Color.BLACK);
    }
}
