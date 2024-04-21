package com.example.echohive;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private Parent root;
    private Stage stage;

    @FXML
    private Button buttonLogin;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Hyperlink loginRedirectToPasswordRecovery;

    @FXML
    private Hyperlink loginRedirectToRegister;

    @FXML
    private TextField loginUserField;

    @FXML
    private Label loginErrorDisplay;

    public void switchToRegister(ActionEvent event) throws IOException {
        //This code snippet replaces the root node of the current scene instead of regenerating the entire scene, useful for saving resources
        root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        //This part gets the source of the event and casts it to a node
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();        
    }

    //Using temporary user data for testing, replace later

    public void loginToHome(ActionEvent event) throws IOException{
        if(loginUserField.getText().toString().equals("testUser") 
        && loginPasswordField.getText().toString().equals("testPassword")) {
            root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.show();                       

        } else if (loginUserField.getText().isEmpty() && loginPasswordField.getText().isEmpty()) {
            loginErrorDisplay.setText("Por favor preencha os campos.");

        } else {
            loginErrorDisplay.setText("Usuário ou senha incorretos.");
        }
    }
}
