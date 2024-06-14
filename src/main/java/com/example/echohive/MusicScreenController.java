package com.example.echohive;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;
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

    @FXML
    private String songLocation;

    public String getSongLocation() {
        return songLocation;
    }

    public void setSongLocation(String songLocation) {
        this.songLocation = songLocation;
    }

    public MusicScreenController(String author, String title, String songpath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MusicScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            songLocation = songpath;
            labelMusicAuthor.setText(author);
            labelMusicTitle.setText(title);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        buttonPlay.setOnMouseClicked(_ -> {
            Context.getInstance().changeMusic(songLocation);
            Context.getInstance().playSong();
            Context.getInstance().updateSliderForPlayer();
        });
    }
}