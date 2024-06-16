package com.example.echohive;
    
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.echohive.DBManager.Manager;
import com.example.echohive.DBManager.Songs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditMscController {

    @FXML
    private AnchorPane editMscStage;

    @FXML
    private TextField musicTitle;

    @FXML
    private Button searchFiles;

    @FXML
    private Label editMscErrorDisplay;

    @FXML
    private Button editMusic;

    @FXML
    private Label title;

    public File file;

    @FXML
    public void editSong() throws IOException {
        Stage stage = (Stage) editMusic.getScene().getWindow();
        String oldTitle = Context.getInstance().getOldSongTitle();

        //Check for existing titles for author
        try {
            if (oldTitle.equals(musicTitle.getText()) && !musicTitle.getText().isEmpty()) {
                editMscErrorDisplay.setText("Autor já tem musica com esse titulo");
            //Check for empty fields
            } else if (musicTitle.getText().isEmpty()) {
                editMscErrorDisplay.setText("A música precisa de um titulo");
            //Check if file is missing
            } else if (!file.exists()) {
                editMscErrorDisplay.setText("Não há arquivos selecionados");
            } else {
                String newFilepathString = "./data/songs" + "/" + file.getName();
                Files.move(file.toPath(), Paths.get("./data/songs", file.getName()));
                Manager.setSongDataByTitle(oldTitle, musicTitle.getText(), newFilepathString);
                stage.close();
            }
        } catch (IOException e) {
            System.out.println("Failed to edit song: " + e);
        }
    }

    @FXML
    public void fileExplorer() throws IOException{
        try {
            Stage stage = new Stage();
            File musicDir = new File(System.getProperty("user.home"));
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                                                "Music files", "*.wav", "*.mp3", "*.m4a", "*.flac");
            fileChooser.setInitialDirectory(musicDir);
            fileChooser.getExtensionFilters().add(filter);
            fileChooser.setTitle("Selecione um arquivo");
            file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                searchFiles.setStyle("-fx-border-radius:34;");
                searchFiles.setStyle("-fx-background-radius:34;");
                searchFiles.setText("Arquivo Enviado");
                searchFiles.setDisable(true);   
            }

        } catch(Exception e) {
            System.out.println("Failed to open file chooser: " + e);
        }
    }
}
