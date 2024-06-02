package com.example.echohive.DBManager;

import java.sql.*;

public class Users {
    public static void newUser(String name, String email, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(Manager.dbLocation);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users(name, email, password) VALUES (?, ?, ?)");

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, password);

        preparedStatement.executeUpdate();

        preparedStatement.close();

        connection.close();
    }

    public static String[] getUserDataByName(String name) throws SQLException{
        String[] data = new String[4];

        Connection connection = DriverManager.getConnection(Manager.dbLocation);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, bio, email, password FROM Users WHERE name = ?");
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();
        if (!rs.isBeforeFirst())
            return data;

        data[0] = rs.getString("name");
        data[1] = rs.getString("bio");
        data[2] = rs.getString("email");
        data[3] = rs.getString("password");

        rs.close();
        preparedStatement.close();
        connection.close();

        return data;
    }

    public static void setColumnForUserByName(String name, Manager.UsersColumns columnType, String data) throws SQLException{
        String column = "";
        switch (columnType){
            case NAME -> column = "name";
            case BIO -> column = "bio";
            case EMAIL -> column = "email";
            case PASSWORD -> column = "password";
        }
        String sqlQuery = "UPDATE Users SET " + column + " = ? WHERE name = ?";

        Connection connection = DriverManager.getConnection(Manager.dbLocation);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

        preparedStatement.setString(1, data);
        preparedStatement.setString(2, name);

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public static String[] getTitlesOfLikedSongsByName(String name) throws SQLException{
        Connection connection = DriverManager.getConnection(Manager.dbLocation);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT liked FROM Users WHERE name = ?");
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.getString("liked") == null) {
            preparedStatement.close();
            connection.close();
            rs.close();

            return null;
        }

        String[] data = rs.getString("liked").split(",");

        preparedStatement.close();
        connection.close();
        rs.close();

        return data;
    }

    public static void addLikedSongsByName(String name, String titleLiked) throws SQLException{
        Connection connection = DriverManager.getConnection(Manager.dbLocation);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Users SET liked = ? WHERE name = ?");

        String[] data = getTitlesOfLikedSongsByName(name);
        if (data == null) {
            preparedStatement.setString(1, titleLiked + ",");

        } else {
            String[] newData = new String[data.length + 1];

            for(int i = 0;i < newData.length;i++){
                if (i < data.length)
                    newData[i] = data[i];
                else newData[i] = titleLiked;
            }

            String liked = "";
            for (String newDatum : newData) {
                liked += newDatum + ",";
            }

            preparedStatement.setString(1, liked);

        }
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();


        preparedStatement.close();
        connection.close();
    }
}
