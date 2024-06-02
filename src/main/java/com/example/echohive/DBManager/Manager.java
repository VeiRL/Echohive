package com.example.echohive.DBManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class Manager {
    public enum UsersColumns{
        NAME,
        BIO,
        EMAIL,
        PASSWORD
    }

    final public static String dbLocation = "jdbc:sqlite:data/content.db";

    public static void createTables(){
        Connection connect = null;
        try {
            /* Create Directory if no exists */
            Path path = Paths.get("./data");
            if (!Files.exists(path))
                Files.createDirectory(path);
            path = Paths.get("./data/Songs");
            if (!Files.exists((path)))
                Files.createDirectory((path));

            connect = DriverManager.getConnection(dbLocation);
            Statement statement = connect.createStatement();
            statement.setQueryTimeout(10);

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        "name STRING NOT NULL, " +
                                        "bio STRING, " +
                                        "email STRING, " +
                                        "password STRING NOT NULL, " +
                                        "profile BLOB, " +
                                        "liked STRING)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Songs (id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                        "title STRING NOT NULL, " +
                                        "author STRING NOT NULL, " +
                                        "path STRING NOT NULL)");

            statement.close();
        } catch (SQLException | IOException e){
            System.out.println(e.getMessage());
        } finally {
            try{
                if (connect != null)
                    connect.close();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    //region Users Table Functions
    public static void newUser(String name, String password){
        try{
            Users.newUser(name, null, password);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void newUser(String name, String email, String password){
        try{
            Users.newUser(name, email, password);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * That function is used for get data from user by name
     * @param name name of user
     * @return Data from user of table in order [name, bio, email, password]
     */
    public static String[] getUserDataByName(String name){
        String[] data = new String[4];
        try {
            data = Users.getUserDataByName(name);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return data;
    }

    /**
     * That function is used for modify any column from table Users
     * @param name name of user
     * @param columnType column to modify
     * @param data new value for column
     */
    public static void setColumnForUserByName(String name, Manager.UsersColumns columnType, String data){
        try{
            Users.setColumnForUserByName(name, columnType, data);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static String[] getTitlesOfLikedSongsByName(String name){
        String[] data = null;
        try{
            data = Users.getTitlesOfLikedSongsByName(name);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return data;
    }

    public static void addLikedSongsByName(String name, String titleLiked){
        try{
            Users.addLikedSongsByName(name, titleLiked);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //endregion

    public static void addSong(String title, String author, File song){
        try{
            Songs.addSong(title, author, song);
        } catch (SQLException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static String getSongLocationByTitle(String title){
        String path = null;
        try{
            path = Songs.getSongLocationByTitle(title);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return path;
    }

    public static void deleteSongByTitle(String title){
        try{
            Songs.deleteSongByTitle(title);
        }catch (SQLException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void setSongTitleByTitle(String title, String newTitle){
        try{
            Songs.setSongTitleByTitle(title, newTitle);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
