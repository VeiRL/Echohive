package com.example.echohive;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.echohive.DBManager.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class HomeController {

    @FXML
    private ScrollPane scroll;

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
    private Hyperlink searchButton;

    @FXML
    private Hyperlink username;

    @FXML
    private ImageView userProfilePic;

    @FXML
    private Label usrShortDesc;

    public void initialize() {
        username.setText(Context.getInstance().currentUser().getUser());
        musicListInstance();
    }

    public void homeToProfile(ActionEvent event) throws IOException {
        MainController.switchScenes("Profile.fxml", username);
    }

    public void homeToSearch(ActionEvent event) throws IOException {
        MainController.switchScenes("search.fxml", searchButton);
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
                if (isLikedSong(rs.getString(2)))
                    idList.add(rs.getString(1));
            }

            rs = statement.executeQuery(sql);

            for(String _: idList) {
                if (isLikedSong(rs.getString(2))) {
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

    private boolean isLikedSong(String title){
        String[] likeds = Manager.getTitlesOfLikedSongsByName(Context.getInstance().currentUser().getUser());
        boolean isLiked = false;

        if (likeds == null)
            return false;

        for (String liked : likeds){
            if (title.equals(liked)) {
                isLiked = true;
                break;
            }
        }
        return isLiked;
    }

    public void playClicker() {
        Context.getInstance().playSong();
    }

    public void pauseClicker() {
        Context.getInstance().pauseSong();
    }

    public void likeClicker() {
        if(!isLikedSong(Context.getInstance().musicName))
            Manager.addLikedSongsByName(Context.getInstance().currentUser().getUser(), Context.getInstance().musicName);
        else if (isLikedSong(Context.getInstance().musicName))
            Manager.removeLikedSongByName(Context.getInstance().currentUser().getUser(), Context.getInstance().musicName);
    }
}
