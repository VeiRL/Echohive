package com.example.echohive;

//Register function still unfinished

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    private Parent root;
    private Stage stage;

    @FXML
    private Button buttonRegister;

    @FXML
    private Hyperlink registerRedirectToLogin;

    @FXML
    private PasswordField registerUserConfirmPassword;

    @FXML
    private TextField registerUserEmail;

    @FXML
    private TextField registerUserField;

    @FXML
    private PasswordField registerUserPassword;

    public void addUser(ActionEvent event) throws IOException {
        //TODO
    }

    public void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }

}
