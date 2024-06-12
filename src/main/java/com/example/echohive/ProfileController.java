package com.example.echohive;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.echohive.DBManager.Manager;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class ProfileController implements Initializable {
 
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private Button importSongs;

    @FXML
    private Slider musicSlider;

    @FXML
    private Label timeLabel;

    @FXML
    private Hyperlink homeButton;

    @FXML
    private Hyperlink searchButton;

    @FXML
    private Label userBio;

    @FXML
    private Label username;

    public void initialize(URL location, ResourceBundle resources) {
        musicListInstance();
    } 

    public void musicListInstance() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        RowConstraints rowConstraints = new RowConstraints();
        columnConstraints.setHgrow(Priority.NEVER);
        columnConstraints.setMaxWidth(200.0);
        columnConstraints.setPrefWidth(200.0);
        rowConstraints.setMaxHeight(200.0);
        rowConstraints.setPrefHeight(200.0);
        GridPane gp = new GridPane();
        gp.getColumnConstraints().add(columnConstraints);
        gp.getRowConstraints().add(rowConstraints);

        ArrayList<String> idList = new ArrayList<String>();
        Connection connection;
        String sql;
        Statement statement;
        ResultSet rs;

        try {
            connection = DriverManager.getConnection(Manager.dbLocation);
            sql = "SELECT id, title, author, path FROM Songs;";
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Adicionando id " + rs.getString(1) + " das musicas ao idList");
                idList.add(rs.getString(1));
                for(String lst: idList) {
                    var msc = new MusicScreenController(rs.getString(3), rs.getString(2), rs.getString(4));
                    gp.getChildren().add(msc);
                }
            }

            scroll.setContent(gp);

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

/*     public void sliderAndTime() throws IOException {
        
        mp.setOnReady(() -> {
            timeLabel.textProperty().bind(
                Bindings.createStringBinding(() -> {
                    Duration time = mp.getCurrentTime();
                    return String.format("%4d:%02d:%04.1f", 
                        (int) time.toHours(),
                        (int) time.toMinutes() % 60,
                        (int) time.toSeconds() % 3600);
                }, mp.currentTimeProperty()));

            musicSlider.maxProperty().bind(
                Bindings.createDoubleBinding(() -> mp.getTotalDuration().toSeconds(),
                mp.totalDurationProperty()));
            mp.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                musicSlider.setValue(newValue.toSeconds());
            });
            musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                mp.seek(Duration.seconds(newValue.doubleValue()));
            });
        });
    } */
    
    public void switchToHome() throws IOException {
        MainController.switchScenes("Home.fxml", homeButton);
    }

    public void switchToSearch() throws IOException {
        MainController.switchScenes("search.fxml", searchButton);
    }

    @FXML
    public void sendMscPopup() throws IOException {
        MainController.loadPopup("SendMsc.fxml", "Enviar Musica");
    }
    
}
