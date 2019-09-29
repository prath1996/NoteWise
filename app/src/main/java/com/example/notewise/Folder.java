package com.example.notewise;

import java.sql.Timestamp;

public class Folder {

    private String FolderName;
    private Timestamp FirstCreated;
    private Timestamp LastModified;

    public Folder(String name) {
        this.FolderName = name;
        this.FirstCreated = new Timestamp(System.currentTimeMillis());
        this.LastModified = this.FirstCreated;
    }

    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String FolderName) {
        this.FolderName = FolderName;
    }

    public Timestamp getFirstCreated() {
        return FirstCreated;
    }
    
    public Timestamp getLastModified() {
        return LastModified;
    }

    public void setLastModified(Timestamp LastModified) {
        this.LastModified = LastModified;
    }


}
