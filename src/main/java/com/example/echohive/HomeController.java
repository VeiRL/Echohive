package com.example.echohive;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.echohive.DBManager.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class HomeController implements Initializable {

    @FXML
    private Hyperlink searchButton;

    @FXML
    private Hyperlink username;

    @FXML
    private ImageView userProfilePic;

    @FXML
    private Label usrShortDesc;

    public void initialize(URL location, ResourceBundle resources) {
        username.setText(Context.getInstance().currentUser().getUser());
    }

    public void homeToProfile(ActionEvent event) throws IOException {
        MainController.switchScenes("Profile.fxml", username);
    }

    public void homeToSearch(ActionEvent event) throws IOException {
        MainController.switchScenes("search.fxml", searchButton);
    }

}
