package com.example.echohive;

public class Context {
    
    private final static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }
    
    private User user = new User();

    public User currentUser() {
        return user;
    }
}
