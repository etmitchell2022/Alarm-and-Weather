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

public class Stopwatch extends Application {

    private VBox vBox = new VBox(30);
    private HBox hBox = new HBox(30);
    private Button startButton = new Button("Start");
    private Button resetButton = new Button("Reset");
    private Text text = new Text("00:00:000");
    private Timeline timeline = new Timeline();
    private int minutes = 0;
    private int secs = 0;
    private int millis = 0;
    private boolean stopwatch = true;
    private Scene scene = new Scene(vBox, 400, 400);
    private Button returnHome = new Button("Home");

    private void change(Text text) {
        if (millis == 1000) {
            secs++;
            millis = 0;
        }
        if (secs == 60) {
            minutes++;
            secs = 0;
        }
        text.setText((((minutes / 10) == 0) ? "0" : "") + minutes + ":"
                + (((secs / 10) == 0) ? "0" : "") + secs + ":"
                + (((millis / 10) == 0) ? "00" : (((millis / 100) == 0) ? "0" : "")) + millis++);
    }

    @Override
    public void start(Stage stage) {

        text.setScaleX(3);
        text.setScaleY(3);
        text.setFill((Color.WHITE));
        timeline = new Timeline(new KeyFrame(Duration.millis(1), (e -> change(text))));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        startButton.setStyle("-fx-background-color: green");
        startButton.setPrefSize(100, 30);
        startButton.setOnAction(e -> startButtonClicked());

        resetButton.setStyle("-fx-background-color: yellow");
        resetButton.setPrefSize(100, 30);
        resetButton.setOnAction(e -> resetButtonClicked());

        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(startButton, resetButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(text, hBox, returnHome);
        vBox.setStyle("-fx-background-color:  #c41e3a");

        returnHome.setStyle("-fx-background-color:  orange");
        returnHome.setPrefSize(100, 30);
        returnHome.setOnAction(e -> returnButtonClicked(stage));

        stage.setScene(scene);
        stage.setTitle("Stopwatch");
        stage.show();
    }

    private void returnButtonClicked(Stage stage) {
        MainUI mainUI = new MainUI();
        mainUI.start(stage);
    }

    private void startButtonClicked() {
        if (stopwatch) {
            timeline.play();
            stopwatch = false;
            startButton.setText("Stop");
            startButton.setStyle("-fx-background-color: red");
        } else {
            timeline.pause();
            stopwatch = true;
            startButton.setText("Start");
            startButton.setStyle("-fx-background-color: green");
        }
    }

    private void resetButtonClicked() {
        minutes = 0;
        secs = 0;
        millis = 0;
        timeline.pause();
        text.setText("00:00:000");
        if (!stopwatch) {
            stopwatch = true;
            startButton.setText("Start");
            startButton.setStyle("-fx-background-color: green");
        }
    }
}