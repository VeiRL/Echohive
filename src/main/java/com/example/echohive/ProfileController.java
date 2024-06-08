package com.example.echohive;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class ProfileController {
 
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private Button importSongs;

    @FXML
    private Hyperlink homeButton;

    @FXML
    private Hyperlink searchButton;

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
        MainController.loadPopup("SendMsc.fxml", "Enviar Musica");
    }
    
}
