package com.example.pascal_pc.tasklist.models;

import java.util.Date;
import java.util.UUID;

public class Task {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mDescription;
    private boolean mDone;

    public Task() {
        mId =UUID.randomUUID();
        mDate=new Date();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    public UUID getId() {
        return mId;
    }


}
