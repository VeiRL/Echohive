package com.example.echohive;

import java.io.File;
import java.io.IOException;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SendMscController {

    @FXML
    private AnchorPane musicAuthor;

    @FXML
    private Label sendMscErrorDisplay;

    @FXML
    private TextField musicTitle;

    @FXML
    private Button searchFiles;

    @FXML
    private Button sendMusic;

    public File file;

    public void uploadSong() {

    }

/*    @FXML
    public void fileExplorer() throws IOException{
        try {

            }
        } catch (IOException e) {
            System.out.println("Failed to open file chooser: " + e);
        }
    }*/
}
