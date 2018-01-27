package view;

// Class that represents a countdown for the timed arena

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MyTimer extends Parent {

    private static final int PREF_ARENA_TIMER_WIDTH = 300;
    private static final int PREF_ARENA_TIMER_HEIGHT = 80;
    private static final int STARTTIME = 40;
    private Timeline timeline;
    private Label timerLabel;
    private IntegerProperty seconds = new SimpleIntegerProperty(STARTTIME);
    //private IntegerProperty minutes = new SimpleIntegerProperty(STARTTIME);

    public MyTimer() {
        Pane pane = new Pane();
        pane.setPrefSize(PREF_ARENA_TIMER_WIDTH, PREF_ARENA_TIMER_HEIGHT);
        timerLabel = new Label();
        Label time_remaining = new Label();
        time_remaining.setMinWidth(Region.USE_PREF_SIZE);
        time_remaining.setMinHeight(Region.USE_PREF_SIZE);
        timerLabel.setMinWidth(Region.USE_PREF_SIZE);
        timerLabel.setMinHeight(Region.USE_PREF_SIZE);
        timerLabel.textProperty().bind(seconds.asString());
        //timerLabel.setText(minutes.toString() + " : " + seconds.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 3em;");
        // Start the timer
       // timerLabel.setText(minutes.toString() + " : " + seconds.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        /*timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*if(seconds.equals(0) && minutes.equals(0)) {
                    timeline.stop();
                }
                else if(seconds.equals(0) && minutes.getValue() > 0) {
                    minutes = new SimpleIntegerProperty(minutes.getValue()-1);
                    seconds = new SimpleIntegerProperty(59);
                }
                else {
                    seconds = new SimpleIntegerProperty(seconds.getValue()-1);
                }*/
               /* if(seconds.getValue() == 0) {
                    timeline.stop();
                }
                else {
                    seconds = new SimpleIntegerProperty(seconds.getValue()-1);
                }
                seconds.set(STARTTIME);
                timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(STARTTIME+1),
                                new KeyValue(seconds, 0)));
                timeline.playFromStart();
            }
        }));*/
        //timeline.playFromStart();

        if (timeline != null) {
            timeline.stop();
        }
        seconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME+1),
                        new KeyValue(seconds, 0)));
        timeline.playFromStart();

        pane.getChildren().add(timerLabel);
        this.getChildren().add(pane);
    }
    public int timerValue() {
        return seconds.getValue();
    }
}
