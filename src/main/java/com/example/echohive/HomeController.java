package com.example.echohive;

import java.io.IOException;

import com.example.echohive.DBManager.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class HomeController {

    @FXML
    private Hyperlink searchButton;

    @FXML
    private Hyperlink username;

    @FXML
    private Circle userProfilePic;

    @FXML
    private Label usrShortDesc;

    public void homeToProfile(ActionEvent event) throws IOException {
        MainController.switchScenes("Profile.fxml", username);
    }

    public void homeToSearch(ActionEvent event) throws IOException {
        MainController.switchScenes("search.fxml", searchButton);
    }

}
