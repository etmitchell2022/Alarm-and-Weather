package edu.bsu.cs222;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

import static edu.bsu.cs222.MainUI.playSound;

public class AlarmUI extends Application {

    private AlarmMechanics alarmMechanics = new AlarmMechanics();
    static boolean snoozeButtonHit = false;
    static boolean dismissButtonHit = false;
    static String soundChosen = "1 second goat.wav";
    private final Text txtTime = new Text();
    private volatile boolean enough = false;
    private Button buttonDismiss = new Button("Dismiss");
    private Button buttonSnooze = new Button("Snooze");
    private Button buttonAddAlarm = new Button("Add Alarm");
    private Button returnMain = new Button("Home");
    private Label labelAlarmSyntax = new Label("Enter time in h:mm a MMM dd format:");
    private TextField inputTime = new TextField();
    private SimpleDateFormat dt = new SimpleDateFormat("h:mm a MMM dd");
    private VBox vb = new VBox(30);
    private HBox hb1 = new HBox(20);
    private HBox hb2 = new HBox(20);
    private Scene HomeScene = new Scene(vb, 400, 400);
    private ComboBox<String> soundChoice = new ComboBox<>();
    private ComboBox<String> AmPm = new ComboBox<>();
    private ComboBox<String> month = new ComboBox<>();
    private ComboBox<String> day = new ComboBox<>();

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
        labelAlarmSyntax.setTextFill(Color.web("#ffffff"));
        txtTime.setFill((Color.WHITE));
        txtTime.setScaleX(2);
        txtTime.setScaleY(2);

        hb1.getChildren().addAll(buttonSnooze, buttonDismiss);
        hb2.getChildren().addAll(inputTime, AmPm, month, day );
        vb.getChildren().addAll(txtTime, labelAlarmSyntax, hb2, soundChoice, buttonAddAlarm, hb1,  returnMain);
        inputTime.setPrefSize(100, 10);
        inputTime.setPromptText("Enter Time");
        buttonSnooze.setPrefSize(100, 30);
        buttonSnooze.setStyle("-fx-background-color: yellow");
        buttonDismiss.setPrefSize(100, 30);
        buttonDismiss.setStyle("-fx-background-color: yellow");
        buttonAddAlarm.setPrefSize(100, 30);
        buttonAddAlarm.setStyle("-fx-background-color: green");
        returnMain.setPrefSize(100, 30);
        returnMain.setStyle("-fx-background-color:  orange");
        soundChoice.setPrefSize(200, 30);
        soundChoice.setStyle("-fx-background-color: yellow");
        soundChoice.setPromptText("Choose Alarm Sound");
        soundChoice.getItems().addAll("Goat Scream", "Dogs Barking", "Fire Truck Horn", "Police Siren", "Submarine Dive Alarm");
        AmPm.setPromptText("AM/PM");
        AmPm.getItems().addAll("AM", "PM");
        AmPm.setStyle("-fx-background-color: yellow");
        month.setPromptText("Month");
        month.getItems().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul", "Oct","Nov", "Dec");
        month.setStyle("-fx-background-color: yellow");
        day.setPromptText("Day");
        day.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
                "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
        day.setStyle("-fx-background-color: yellow");

        //Sets a 5 minute timer and then restarts the alarmMechanics when it gets to 0
        buttonSnooze.setOnAction(e -> buttonSnoozeClicked(stage));
        buttonDismiss.setOnAction(e -> buttonDismissClicked());
        buttonAddAlarm.setDefaultButton(true);
        buttonAddAlarm.setOnAction(e -> buttonAddAlarmClicked());
        returnMain.setOnAction(e -> returnButtonClicked(stage));
        soundChoice.setOnAction(e -> comboBoxClicked());

        buttonSnooze.setDisable(true);
        buttonDismiss.setDisable(true);

        vb.setAlignment(Pos.CENTER);
        hb1.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);
        stage.setScene(HomeScene);

        liveClock.start();
        stage.show();
    }

    private void comboBoxClicked() {
        if (soundChoice.getValue().equals("Goat Scream")) {
            soundChosen = "1 second goat.wav";
        }
        if (soundChoice.getValue().equals("Dogs Barking")) {
            soundChosen = "dogs2seconds.wav";
        }
        if (soundChoice.getValue().equals("Fire Truck Horn")) {
            soundChosen = "firetruckhorn.wav";
        }
        if (soundChoice.getValue().equals("Police Siren")) {
            soundChosen = "policesiren.wav";
        }
        if (soundChoice.getValue().equals("Submarine Dive Alarm")) {
            soundChosen = "submarine.wav";
        }
    }

    private void returnButtonClicked(Stage stage) {
        MainUI mainUI = new MainUI();
        mainUI.start(stage);
    }

    private void buttonAddAlarmClicked() {
        buttonDismiss.setDisable(false);
        buttonSnooze.setDisable(false);
        String inputString = inputTime.getText() + " " + AmPm.getValue() + " " + month.getValue() + " " + day.getValue();
        System.out.println(inputString);
        alarmMechanics.comparisonLoopInitializer(inputString);
    }

    private void buttonSnoozeClicked(Stage stage) {
        playSound = false;
        snoozeButtonHit = true;
        Snooze snooze = new Snooze();
        snooze.start(stage);
        buttonSnooze.setDisable(true);
        buttonDismiss.setDisable(true);
    }

    private void buttonDismissClicked() {
        playSound = false;
        dismissButtonHit = true;
        buttonDismiss.setDisable(true);
        buttonSnooze.setDisable(true);
    }

    public void stop() {
        this.enough = true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
