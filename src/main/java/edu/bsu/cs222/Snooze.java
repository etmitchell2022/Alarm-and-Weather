package edu.bsu.cs222;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import static edu.bsu.cs222.MainUI.playSound;
import static edu.bsu.cs222.MainUI.soundPlayer;

public class Snooze extends Application {

    private VBox vBox = new VBox(30);
    private HBox hBox = new HBox(30);
    private Button CancelButton = new Button("Cancel");

    private Text text = new Text("05:00");
    private Text snoozeText = new Text("Alarm was snoozed");
    private Timeline timeline = new Timeline();

    private int mins = 5;
    private int secs = 0;

    private boolean timer = true;
    private Scene scene = new Scene(vBox, 400, 400);

    private void change(Text text) {
        if (secs == -1) {
            if (mins > 0) {
                mins--;
                secs = 59;
            }
        }

        if (mins == -1) {
            mins = 0;
        }

        if (mins == 0 && secs == 0) {
            timeline.pause();
            playSound = true;
            while (true){
                for(int i = 0; i<5; i++) soundPlayer.WAVPlayer();
                break;
            }
            playSound = false;
        }
        text.setText((((mins / 10) == 0) ? "0" : "") + mins + ":"
                + (((secs / 10) == 0) ? "0" : "") + secs--);
    }

    @Override
    public void start(Stage stage) {

        text.setScaleX(3);
        text.setScaleY(3);
        text.setFill((Color.WHITE));

        snoozeText.setScaleX(3);
        snoozeText.setScaleY(3);
        snoozeText.setFill((Color.WHITE));

        timeline = new Timeline(new KeyFrame(Duration.millis(1000), (e -> change(text))));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        if (timer){
            timeline.play();
            timer = false;
        }

        hBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(snoozeText,text, hBox, CancelButton);
        vBox.setStyle("-fx-background-color:  #c41e3a");

        CancelButton.setPrefSize(100, 30);
        CancelButton.setOnAction(e -> CancelButtonClicked(stage));

        stage.setScene(scene);
        stage.setTitle("Snooze");
        stage.show();
    }

    private void CancelButtonClicked(Stage stage) {
        timeline.pause();
        AlarmUI alarmUI = new AlarmUI();
        alarmUI.start(stage);
    }
}