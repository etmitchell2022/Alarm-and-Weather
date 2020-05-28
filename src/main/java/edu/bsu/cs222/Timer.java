package edu.bsu.cs222;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import static edu.bsu.cs222.MainUI.soundPlayer;

public class Timer extends Application {

    private VBox vBox = new VBox(30);
    private HBox hBox1 = new HBox(30);
    private HBox hbox2= new HBox(30);
    private Button startButton = new Button("Start");

    private Text text = new Text("00:00");
    private Text mt = new Text("Minutes");
    private Text st = new Text("Seconds");
    private Timeline timeline = new Timeline();

    private Spinner<Integer> m = new Spinner<>(0, 59, 0);
    private Spinner<Integer> s = new Spinner<>(0, 59, 0);

    private int mins;
    private int secs;

    private boolean timer = true;
    private Scene scene = new Scene(vBox, 400, 400);
    private Button returnHome = new Button("Home");

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
            boolean playSound = true;
            while (playSound){
                for(int i = 0; i<5; i++) soundPlayer.WAVPlayer();
                break;
            }
        }
        text.setText((((mins / 10) == 0) ? "0" : "") + mins + ":"
                + (((secs / 10) == 0) ? "0" : "") + secs--);
    }

    @Override
    public void start(Stage stage) {

        text.setScaleX(3);
        text.setScaleY(3);
        text.setFill((Color.WHITE));
        mt.setFill(Color.WHITE);
        st.setFill(Color.WHITE);
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), (e -> change(text))));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        startButton.setStyle("-fx-background-color: green");
        startButton.setPrefSize(100, 30);
        startButton.setOnAction(e -> startButtonClicked());

        hBox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(mt, m);
        hbox2.getChildren().addAll(st,s);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(text, startButton, returnHome, hBox1, hbox2);
        vBox.setStyle("-fx-background-color:  #c41e3a");

        returnHome.setStyle("-fx-background-color:  orange");
        returnHome.setPrefSize(100, 30);
        returnHome.setOnAction(e -> returnButtonClicked(stage));

        stage.setScene(scene);
        stage.setTitle("Timer");
        stage.show();
    }

    private void returnButtonClicked(Stage stage) {
        MainUI mainUI = new MainUI();
        mainUI.start(stage);
    }

    private void startButtonClicked() {
        if (timer) {
            mins = m.getValue();
            secs = s.getValue();
            timeline.play();
            timer = false;
            startButton.setText("Cancel");
            startButton.setStyle("-fx-background-color: red");
        } else {
            timeline.pause();
            timer = true;
            text.setText("00:00");
            startButton.setText("Start");
            startButton.setStyle("-fx-background-color: green");
        }
    }
}