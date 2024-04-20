package com.example.echohive;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Echohive extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Echohive.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("EchoHive");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setWidth(720);
        stage.setHeight(512);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}