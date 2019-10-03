package com.example.notewise;

import android.util.Log;

import java.sql.Timestamp;

public class TodoElement implements FileElement {

    private String id;
    private String text;
    private boolean completed;
    private Timestamp lastModified;

    public TodoElement(String text) {
        this.text = text;
    }

    public TodoElement() {
    }

    public void hello() {
        Log.println(1,"12", "hello");
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        this.lastModified = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public Timestamp getLastModified() {
        return lastModified;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.lastModified = new Timestamp(System.currentTimeMillis());
    }

}
