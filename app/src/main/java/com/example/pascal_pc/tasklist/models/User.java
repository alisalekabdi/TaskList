package com.example.pascal_pc.tasklist.models;

import android.arch.persistence.room.Update;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity()
public class User {

    @Id(autoincrement = true)
    private Long mUserId;

    public User(String mUserName, String mPassword) {
        this.mUserName = mUserName;
        this.mPassword = mPassword;
    }

    @Unique@Update
    private String mUserName;
    @Update
    private String mPassword;
    @Generated(hash = 2122866532)
    public User(Long mUserId, String mUserName, String mPassword) {
        this.mUserId = mUserId;
        this.mUserName = mUserName;
        this.mPassword = mPassword;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getMUserId() {
        return this.mUserId;
    }
    public void setMUserId(Long mUserId) {
        this.mUserId = mUserId;
    }
    public String getMUserName() {
        return this.mUserName;
    }
    public void setMUserName(String mUserName) {
        this.mUserName = mUserName;
    }
    public String getMPassword() {
        return this.mPassword;
    }
    public void setMPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
