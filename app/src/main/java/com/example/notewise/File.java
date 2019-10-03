package com.example.notewise;

import java.sql.Timestamp;
import java.util.List;

public abstract class File {
    private String Name;
    private Timestamp LastModified;
    private int UniqueID;
    private List<FileElement>ListOfElements;

    public File(String name) {
        this.Name = name;
        this.LastModified = new Timestamp(System.currentTimeMillis());
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public List getListOfElement() {
        return ListOfElements;
    }

    public void addElement(NoteElement Element) {
        this.ListOfElements.add(Element);
    }

    abstract int getType();
}
