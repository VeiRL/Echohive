package com.example.echohive;

import java.io.IOException;
import java.sql.SQLException;

import com.example.echohive.DBManager.Songs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
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

    private String songLocation;

    public String getSongLocation() {
        return songLocation;
    }

    public void setSongLocation(String songLocation) {
        this.songLocation = songLocation;
    }

    public MusicScreenController(String author, String title, String songpath) {
        Tooltip tooltip = new Tooltip();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MusicScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            songLocation = songpath;
            labelMusicAuthor.setText(author);
            labelMusicTitle.setText(title);
            tooltip.setText(title);
            Tooltip.install(labelMusicTitle, tooltip);
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        buttonPlay.setOnMouseClicked(_ -> {
            Context.getInstance().musicName = title;
            Context.getInstance().changeMusic(songLocation);
            Context.getInstance().playSong();
            Context.getInstance().updateSliderForPlayer();
        });

        playIcon.setOnMouseClicked(_ -> {
            Context.getInstance().musicName = title;
            Context.getInstance().changeMusic(songLocation);
            Context.getInstance().playSong();
            Context.getInstance().updateSliderForPlayer();
        });
    }

    public void callContextMenu(ContextMenuEvent cmEvent) {
        final ContextMenu cm = new ContextMenu();
        MenuItem edit = new MenuItem("Edit");
        MenuItem delete = new MenuItem("Delete");

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Context.getInstance().setOldSongTitle(labelMusicTitle.getText());
                    MainController.loadPopup("EditMsc.fxml", songLocation);
                } catch (IOException e) {
                    System.out.println("Failed to load popup: " + e);
                }
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Songs.deleteSongByTitle(labelMusicTitle.getText());

                } catch (SQLException | IOException e) {
                    System.out.println("Failed to delete song: " + e);
                }
            }
        });
        cm.getItems().addAll(edit, delete);

        musicWindow.setOnContextMenuRequested(e -> {cm.show(musicWindow, e.getScreenX(), e.getScreenY());}); 
        musicWindow.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {cm.hide();});
    }
}