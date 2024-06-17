package com.example.echohive;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Context {
    
    private final static Context instance = new Context();
    private final SimpleStringProperty oldSongTitle = new SimpleStringProperty();

    public String getOldSongTitle() {
        return oldSongTitle.get();
    }

    public void setOldSongTitle(String value) {
        oldSongTitle.set(value);
    }

    public static Context getInstance() {
        return instance;
    }
    
    private final User user = new User();

    public User currentUser() {
        return user;
    }

    private MediaPlayer player;
    private boolean isPlayerChanged = false;
    public Slider slider;
    public Label currentTimeLabel, maxTimeLabel;
    public String musicName;

    public void changeMusic(String musicLocation){
        if (player == null){
            player = new MediaPlayer(new Media(new File(musicLocation).toURI().toString()));
        }
        else if (player.getStatus() == MediaPlayer.Status.PLAYING) {
            player.stop();
            player = new MediaPlayer(new Media(new File(musicLocation).toURI().toString()));
        }
        isPlayerChanged = true;
    }

    public void playSong(){
        if (player == null) return;
        if ((player.getStatus().equals(MediaPlayer.Status.READY) || player.getStatus().equals(MediaPlayer.Status.PAUSED)))
            player.play();
    }

    public void pauseSong(){
        if (player == null) return;
        if (player.getStatus() == MediaPlayer.Status.PLAYING && player != null)
            player.pause();
    }

    public void updateSliderForPlayer(){
        if (player == null) return;
        if (slider == null) return;
        if (!isPlayerChanged) return;

        player.setOnReady(() -> {
            currentTimeLabel.textProperty().bind(
                Bindings.createStringBinding(() -> {
                    Duration time = player.getCurrentTime();
                    return String.format("%02d:%02d", 
                        (int) time.toMinutes() % 60,
                        ((int) time.toSeconds() % 60));
                }, player.currentTimeProperty()));

            maxTimeLabel.textProperty().bind
                (Bindings.createStringBinding(() -> {
                    Duration maxTime = player.getMedia().getDuration();
                    return String.format("%02d:%02d",
                        (int) maxTime.toMinutes() % 60,
                        ((int) maxTime.toSeconds() % 60));
                }, player.stopTimeProperty()));    

            slider.maxProperty().bind( 
                    Bindings.createDoubleBinding(() -> 
                            player.getTotalDuration().toSeconds(),
                            player.totalDurationProperty()));

            player.currentTimeProperty().addListener((_, _, newValue) -> {
                slider.setValue(newValue.toSeconds());
            });
        });

        isPlayerChanged = false;
    }

    public boolean playerChanged(){
        return isPlayerChanged;
    }
}
