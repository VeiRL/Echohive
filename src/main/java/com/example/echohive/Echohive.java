package com.example.echohive;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Echohive extends Application {
    
    
    @Override
    public void start(Stage stage) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(Echohive.class.getResource("Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("EchoHive 0.0.1");
        stage.setScene(scene);
        stage.setResizable(false);   

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
