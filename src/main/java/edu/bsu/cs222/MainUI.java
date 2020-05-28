package edu.bsu.cs222;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainUI extends Application {

    static boolean playSound = false;
    private final Text txtTime = new Text();
    static SoundPlayer soundPlayer = new SoundPlayer();
    private volatile boolean enough = false;
    private Button buttonAlarm = new Button("Alarm");
    private Button buttonStopwatch = new Button("Stopwatch");
    private Button buttonTimer = new Button("Timer");
    private Button buttonWeather = new Button("Weather");
    private Button Exit = new Button("Exit");
    private SimpleDateFormat dt = new SimpleDateFormat("h:mm a MMM dd");
    private VBox vb = new VBox(30);
    private HBox hb1 = new HBox(20);
    private HBox hb2 = new HBox(20);
    private Scene HomeScene = new Scene(vb, 400, 400);
    private Stopwatch stopwatch = new Stopwatch();
    private Timer timer = new Timer();
    private AlarmUI alarmUI = new AlarmUI();
    private WeatherUI weatherUI = new WeatherUI();

    private Thread liveClock = new Thread(() -> {
        while (!this.enough) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
            String time = dt.format(new Date());
            Platform.runLater(() -> this.txtTime.setText(time));
        }
    });

    @Override
    public void start(Stage stage) {
        stage.setTitle("BSU Alarm Application");
        vb.setStyle("-fx-background-color: #c41e3a");
        txtTime.setFill((Color.WHITE));
        txtTime.setScaleX(3);
        txtTime.setScaleY(3);
        hb1.getChildren().addAll(buttonAlarm, buttonWeather);
        hb2.getChildren().addAll(buttonStopwatch, buttonTimer);
        vb.getChildren().addAll(txtTime, hb1, hb2, Exit);

        buttonAlarm.setPrefSize(100, 30);
        buttonAlarm.setStyle("-fx-background-color: yellow");
        buttonStopwatch.setPrefSize(100, 30);
        buttonStopwatch.setStyle("-fx-background-color: yellow");
        buttonTimer.setPrefSize(100, 30);
        buttonTimer.setStyle("-fx-background-color: yellow");
        buttonWeather.setPrefSize(100, 30);
        buttonWeather.setStyle("-fx-background-color:  yellow");
        Exit.setPrefSize(100, 30);
        Exit.setStyle("-fx-background-color: orange");

        buttonStopwatch.setOnAction(e -> stopwatch.start(stage));
        buttonTimer.setOnAction(e -> timer.start(stage));
        Exit.setOnAction(e -> System.exit(0));
        buttonWeather.setOnAction(e -> {
            try {
                weatherUI.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonAlarm.setOnAction(e -> {
            try {
                alarmUI.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        vb.setAlignment(Pos.CENTER);
        hb1.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);
        stage.setScene(HomeScene);

        liveClock.start();
        stage.show();
    }

    public void stop() {
        this.enough = true;
    }
}