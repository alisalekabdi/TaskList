package com.example.pascal_pc.tasklist.models;

import java.util.UUID;

public class User {
    private String mUserName="";
    private String mPassword="";
    private UUID userId;
    public UUID getUserId() {
        return userId;
    }

    public User() {
        userId=UUID.randomUUID();
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String user) {
        mUserName = user;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
