package com.example.echohive;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class MainController {

    public static void switchScenes(String sceneName, Hyperlink hyperlink) throws IOException {
        try {
            Parent root = FXMLLoader.load(Echohive.class.getResource(sceneName));
            hyperlink.getScene().setRoot(root);    
        } catch (Exception e) {
           System.out.println("Failed to switch scenes: " + e);
        }
    }

    public static void switchScenes(String sceneName, Button button) throws IOException {
        try {
            Parent root = FXMLLoader.load(Echohive.class.getResource(sceneName));
            button.getScene().setRoot(root);    
        } catch (Exception e) {
           System.out.println("Failed to switch scenes: " + e);
        }
    }

    public static void switchScenes(String sceneName, Label label) throws IOException {
        try {
            Parent root = FXMLLoader.load(Echohive.class.getResource(sceneName));
            label.getScene().setRoot(root);    
        } catch (Exception e) {
           System.out.println("Failed to switch scenes: " + e);
        }
    }

    public static void loadPopup(String popupSceneName, String windowName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Echohive.class.getResource(popupSceneName));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.setTitle(windowName);
        stage.setScene(new Scene(root1));
        stage.show();
    }
}