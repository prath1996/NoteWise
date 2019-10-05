package com.example.notewise;

import java.sql.Timestamp;
import java.util.List;

public abstract class File {
    private String name;
    private Timestamp lastModified;
    private int uniqueID;
    private List<FileElement> elements;

    public File(String name) {
        this.name = name;
        this.lastModified = new Timestamp(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public void setID(int id) {
        this.uniqueID = id;
    }

    public int getID() {
        return uniqueID;
    }

    public List getElements() {
        return elements;
    }

    public void addElement(FileElement Element) {
        this.elements.add(Element);
    }

    public void deleteElement(int index) {
        this.elements.remove(index);
    }

    public void updateElement(int index, String newContent) {
        this.elements.get(index).setText(newContent);
    }

    abstract int getType();
}
