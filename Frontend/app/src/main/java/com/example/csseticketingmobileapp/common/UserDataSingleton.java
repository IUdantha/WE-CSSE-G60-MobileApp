package com.example.csseticketingmobileapp.common;

public class UserDataSingleton {
    private static UserDataSingleton instance;
    private String userId;
    private String email;
    private String role;
    private  String fullName;

    private UserDataSingleton() {
        // Private constructor to prevent instantiation
    }

    public static synchronized UserDataSingleton getInstance() {
        if (instance == null) {
            instance = new UserDataSingleton();
        }
        return instance;
    }

    public void setUserDataSingleton(String userId, String email, String role, String fullName) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }
}
