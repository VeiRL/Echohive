package com.example.echohive;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.echohive.DBManager.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private Label registerErrorDisplay;

    @FXML
    private Label passwordErrorDisplay;

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
        String usrName = registerUserField.getText();
        String usrPass = registerUserPassword.getText();
        String usrPassConfirm = registerUserConfirmPassword.getText();
        String usrEmail = registerUserEmail.getText();
        
        try {
            PreparedStatement pst;
            Connection connection = DriverManager.getConnection(Manager.dbLocation);
            String sql = "SELECT * from Users WHERE name LIKE ?; ";
            pst = connection.prepareStatement(sql);
            pst.setString(1, usrName);
            ResultSet rs = pst.executeQuery();

            //Check for existing usernames
            if(rs.next() && !registerUserField.getText().isBlank()) {
                registerErrorDisplay.setText("usuário já existe");
            //Check for blank fields
            } else if(registerUserPassword.getText().isBlank() || registerUserConfirmPassword.getText().isBlank() || 
                      registerUserField.getText().isBlank()) {
                        
                registerErrorDisplay.setText("há um ou mais campos vazios");
            //Check for confirm password match
            } else if (!usrPass.equals(usrPassConfirm)) {
                registerErrorDisplay.setText("As senhas não são iguais");
            //Adding user to DB
            } else {
                Manager.newUser(usrName, usrEmail, usrPass);
                MainController.switchScenes("login.fxml", buttonRegister);
            }

            connection.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("Registration failed " + e);
        }
    }

    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        MainController.switchScenes("login.fxml", registerRedirectToLogin);
    }

}
