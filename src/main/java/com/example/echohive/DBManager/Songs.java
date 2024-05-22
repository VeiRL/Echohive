package com.example.echohive.DBManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;

public class Songs {
    private final static String songsLocation = "./data/songs";

    public static void addSong(String title, String author, File song) throws SQLException, IOException {
        Files.move(song.toPath(), Paths.get(songsLocation, song.getName()));

        String path = songsLocation + "/" + song.getName();

        Connection connection = DriverManager.getConnection(Manager.dbLocation);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Songs(title, author, path) VALUES (?, ?, ?)");

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        preparedStatement.setString(3, path);

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public static String getSongLocationByTitle(String title) throws SQLException{
        String path;

        Connection connection = DriverManager.getConnection(Manager.dbLocation);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT path FROM Songs WHERE title = ?");

        preparedStatement.setString(1, title);

        ResultSet rs = preparedStatement.executeQuery();

        if (!rs.isBeforeFirst())
            return null;

        path = rs.getString("path");

        preparedStatement.close();
        connection.close();
        rs.close();

        return path;
    }

    public static void deleteSongByTitle(String title) throws SQLException, IOException{
        File song = new File(Objects.requireNonNull(getSongLocationByTitle(title)));
        if (song.delete()){
            Connection connection = DriverManager.getConnection(Manager.dbLocation);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Songs WHERE title = ?");

            preparedStatement.setString(1, title);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } else {
            throw new IOException("Error to delete file.");
        }
    }

    public static void setSongTitleByTitle(String title, String newTitle) throws SQLException{
        Connection connection = DriverManager.getConnection(Manager.dbLocation);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Songs SET title = ? WHERE title = ?");

        preparedStatement.setString(1, newTitle);
        preparedStatement.setString(2, title);

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
