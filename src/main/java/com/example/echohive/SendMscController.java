package com.example.echohive;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.echohive.DBManager.Manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SendMscController {

    @FXML
    private Label sendMscErrorDisplay;

    @FXML
    private TextField musicTitle;

    @FXML
    private TextField musicAuthor;

    @FXML
    private Button searchFiles;

    @FXML
    private Button sendMusic;

    public File file;

    public void uploadSong() {
        String[] mscData = new String[2];
        String title = musicTitle.getText();
        String author = musicAuthor.getText();
        
        try {
            Connection con = DriverManager.getConnection(Manager.dbLocation);
            String sql = "SELECT title, author from Songs WHERE title LIKE ?; ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, title);
            ResultSet rs = pst.executeQuery();
            mscData[0] = rs.getString("title");
            mscData[1] = rs.getString("author");

            //Check for existing titles for author
            if (rs.next() && author.equals(mscData[1]) && !musicTitle.getText().isEmpty()) {
                sendMscErrorDisplay.setText("Autor já tem musica com esse titulo");
            //Check for empty fields
            } else if (musicTitle.getText().isEmpty() || musicAuthor.getText().isEmpty()) {
                sendMscErrorDisplay.setText("Há um ou mais campos vazios");
            //Check if file is missing
            } else if (!file.exists()) {
                sendMscErrorDisplay.setText("Não há arquivos selecionados");
            } else {
                Manager.addSong(title, author, file);   
            }
        } catch (SQLException e) {
            System.out.println("Failed to add music: " + e);
        }
    }

    @FXML
    public void fileExplorer() throws IOException{
        try {
            Stage stage = new Stage();
            File musicDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Music");
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                                                "Music files", "*.wav", "*.mp3", "*.m4a", "*.flac");
            fileChooser.setInitialDirectory(musicDir);
            fileChooser.getExtensionFilters().add(filter);
            fileChooser.setTitle("Selecione um arquivo");
            file = fileChooser.showOpenDialog(stage);

        } catch(Exception e) {
            System.out.println("Failed to open file chooser: " + e);
        }
    }
}
