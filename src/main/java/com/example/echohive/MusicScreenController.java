package com.example.echohive;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;
import javafx.scene.media.Media;
import javafx.scene.layout.AnchorPane;

public class MusicScreenController extends AnchorPane {

    @FXML
    private Button buttonPlay;

    @FXML
    private Polygon playIcon;

    @FXML
    private AnchorPane musicWindow;

    @FXML
    private Label labelMusicAuthor;

    @FXML
    private Label labelMusicTitle;

    private String songLocation;

    public MusicScreenController(String author, String title, String songpath) {

        songLocation = songpath;
        FXMLLoader fxmlLoader = new FXMLLoader(Echohive.class.getResource("MusicScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        initElements();
    }

    void initElements() {
        buttonPlay.onMouseClickedProperty().set(event -> playSong());

    }

    void playSong() {
        MediaPlayer player = new MediaPlayer(null);
    }
}