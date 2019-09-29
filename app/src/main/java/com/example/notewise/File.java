package com.example.notewise;

import java.sql.Timestamp;

public abstract class File {
    private String Name;
    private Timestamp FirstCreated;
    private Timestamp LastModified;
    private int UniqueID;

    public File(String name) {
        this.Name = name;
        this.FirstCreated = new Timestamp(System.currentTimeMillis());
        this.LastModified = this.FirstCreated;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Timestamp getFirstCreated() {
        return FirstCreated;
    }

    public void setFirstCreated(Timestamp firstCreated) {
        FirstCreated = firstCreated;
    }

    public Timestamp getLastModified() {
        return LastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        LastModified = lastModified;
    }

    public void setID(int id) {
        this.UniqueID = id;
    }

    public int getID() {
        return UniqueID;
    }


    abstract int getType();
}
