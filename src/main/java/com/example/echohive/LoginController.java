package com.example.echohive;

import java.io.IOException;

import com.example.echohive.DBManager.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

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
        MainController.switchScenes("Register.fxml", loginRedirectToRegister);
    }

    public void loginToHome(ActionEvent event) throws IOException{
        String username = loginUserField.getText();
        String password = loginPasswordField.getText();
        String data[] = Manager.getUserDataByName(username);

        //Checking for username in DB
        if (username.equals(data[0]) && password.equals(data[3])) {
            Context.getInstance().currentUser().setUser(loginUserField.getText());
            MainController.switchScenes("Home.fxml", buttonLogin);
        //Checking for blank fields
        } else if (username.equals("") && password.equals("")) {
            loginErrorDisplay.setText("Usuário e senha vazios");
            loginPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red;");
            loginUserField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red;");
        } else {
            loginErrorDisplay.setText("Usuário ou senha incorretos");
            loginPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red;");
            loginUserField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red;");
        }
    }
}
