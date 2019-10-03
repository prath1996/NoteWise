package com.example.notewise;

import java.sql.Time;
import java.sql.Timestamp;

interface FileElement {
    public String getID();

    public void setID(String id);

    public String getText();

    public void setText(String text);

    public Timestamp getLastModified();

    public boolean isCompleted();

    public void setCompleted(boolean completed);
}
