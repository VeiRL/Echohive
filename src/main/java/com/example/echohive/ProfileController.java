package com.example.echohive;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProfileController {

    @FXML
    private Label homeButton;

    @FXML
    private Button importSongs;

    @FXML
    private Label searchButton;

    @FXML
    private Label userBio;

    @FXML
    private Label username;

    public void switchToHome() throws IOException {
        MainController.switchScenes("Home.fxml", homeButton);
    }

    public void switchToSearch() throws IOException {
        MainController.switchScenes("search.fxml", searchButton);
    }

    @FXML
    public void sendMscPopup() throws IOException {
        MainController.loadPopup("SendMsc.fxml");
    }

}
