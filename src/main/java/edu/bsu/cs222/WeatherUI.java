package edu.bsu.cs222;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherUI extends Application {
    private Button home = new Button("Home");
    private Button getWeather = new Button("Click for Weather");
    private VBox vBox = new VBox(30);
    private Scene scene = new Scene(vBox, 400, 400);
    private TextArea weatherOutput = new TextArea ();
    private SimpleDateFormat dt = new SimpleDateFormat("h:mm a MMM dd");

    private Thread liveClock = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
            String time = dt.format(new Date());
            Platform.runLater(() -> this.txtTime.setText(time));
        }
    });

    private final Text txtTime = new Text();

    @Override
    public void start(Stage stage) {
        vBox.setStyle("-fx-background-color:  #c41e3a");
        home.setPrefSize(50, 30);
        home.setStyle("-fx-background-color:  orange");
        weatherOutput.setPrefRowCount(5);
        weatherOutput.setPrefColumnCount(3);
        vBox.setPadding(new Insets(1,30,1,30));
        getWeather.setVisible(true);
        getWeather.setStyle("-fx-background-color: green");
        weatherOutput.setVisible(true);
        weatherOutput.setEditable(false);
        txtTime.setFill((Color.WHITE));
        txtTime.setScaleX(2);
        txtTime.setScaleY(2);
        vBox.getChildren().addAll(txtTime,getWeather, weatherOutput,home);
        vBox.setAlignment(Pos.CENTER);

        WeatherAPI weatherAPI = new WeatherAPI();
        weatherAPI.APIAccess();
        String temp = weatherAPI.printTemp();
        String chanceOfRain = weatherAPI.printChanceOfRain();
        String precipitationType = weatherAPI.printPrecipitationType();
        String precipitationIntensity = weatherAPI.printPrecipitationIntensity();
        weatherOutput.setText(temp + "\n" + chanceOfRain + "\n" + precipitationType + "\n" + precipitationIntensity);

        home.setOnAction(e->returnButtonClicked(stage));
        getWeather.setOnAction(e->weatherButtonClicked());

        stage.setScene(scene);
        stage.setTitle("BSU Weather");
        liveClock.start();
        stage.show();
    }

    private void returnButtonClicked(Stage stage) {
        MainUI mainUI = new MainUI();
        mainUI.start(stage);
    }

    private void weatherButtonClicked(){
        TTS tts = new TTS();
        weatherOutput.setVisible(true);
        getWeather.setVisible(false);
        tts.readTTS("");
    }
}


