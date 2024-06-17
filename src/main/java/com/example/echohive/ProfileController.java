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

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class ProfileController implements Initializable {
 
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private Button importSongs;

    @FXML
    private ImageView pauseIco;

    @FXML
    private ImageView playIco;

    @FXML
    private Slider musicSlider;

    @FXML
    private Label timeLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Hyperlink homeButton;

    @FXML
    private Hyperlink searchButton;

    @FXML
    private Label userBio;

    @FXML
    private Label username;

    public void initialize(URL location, ResourceBundle resources) {
        username.setText(Context.getInstance().currentUser().getUser());
        musicListInstance();
    } 

    public void musicListInstance() {
        int gridCol = 0, gridRow = 0;
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

        ArrayList<String> idList = new ArrayList<>();
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
                if (rs.getString(3).equals(Context.getInstance().currentUser().getUser()))
                    idList.add(rs.getString(1));
            }

            rs = statement.executeQuery(sql);

            for(String _ : idList) {
                if (rs.getString(3).equals(Context.getInstance().currentUser().getUser())) {
                    rs.next();
                    var msc = new MusicScreenController(rs.getString(3), rs.getString(2), rs.getString(4));
                    gp.setPadding(new Insets(10));
                    gp.setHgap(5);
                    gp.setVgap(5);

                    gp.add(msc, gridCol, gridRow);
                    gridCol++;

                    if (gridCol > 3) {
                        //Reset Column
                        gridCol = 0;
                        //Next Row
                        gridRow++;
                    }
                }
            }

            scroll.setContent(gp);
            scroll.setPannable(true);

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Context.getInstance().slider = musicSlider;
        Context.getInstance().currentTimeLabel = timeLabel;
        Context.getInstance().maxTimeLabel = durationLabel;
    }


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

    public void playClicker(MouseEvent mouseEvent) {
        Context.getInstance().playSong();
    }

    public void pauseClicker(MouseEvent mouseEvent) {
        Context.getInstance().pauseSong();
    }
}
