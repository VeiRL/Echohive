package com.example.echohive;

import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleStringProperty user = new SimpleStringProperty("");

    public User() {
    }

    public String getUser() {
        return user.get();
    }

    public void setUser(String value) {
        user.set(value);
    }

    @Override
    public String toString() {
        return getUser();
    }
}
