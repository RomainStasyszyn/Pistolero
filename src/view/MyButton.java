package view;

// Class that represents a button in menu of the game

import controller.MyButtonController;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MyButton extends StackPane {

    private static final int PREF_BUTTON_WIDTH = 300;
    private static final int PREF_BUTTON_HEIGHT = 40;
    private static final float PREF_BUTTON_OPACITY = 0.8f;

    private Rectangle rectangle;
    private Text text;
    private MyButtonController myButtonController;

    public MyButton(String name) {
        text = new Text(name);
        text.setFont(Font.font(20));
        text.setFill(Color.WHITE);

        rectangle = new Rectangle(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        rectangle.setOpacity(PREF_BUTTON_OPACITY);
        rectangle.setFill(Color.GREY);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(rectangle, text);

        myButtonController = new MyButtonController(this);

        this.setOnMouseExited(event -> {
            myButtonController.MyButtonExcited();
        });

        this.setOnMouseEntered(event -> {
            myButtonController.MyButtonEntered();
        });
    }

    public Rectangle getRectangle() { return this.rectangle; }
}
