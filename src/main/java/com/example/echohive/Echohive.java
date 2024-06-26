package com.example.echohive;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.echohive.DBManager.Manager;

import java.io.IOException;

public class Echohive extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Echohive.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("EchoHive");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Manager.createTables();
    }

    public static void main(String[] args) {
        launch();
    }
}
