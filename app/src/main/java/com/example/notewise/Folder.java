package com.example.notewise;

import com.noodle.Id;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class Folder {

    @Id
    long id;

    private String folderName;
    private Timestamp firstCreated;
    private Timestamp lastModified;
    private HashMap<String, File> files;

    public Folder(String name) {
        this.folderName = name;
        this.firstCreated = new Timestamp(System.currentTimeMillis());
        this.lastModified = this.firstCreated;
    }


    // Folder
    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getName() {
        return folderName;
    }

    public void setName(String folderName) {
        this.folderName = folderName;
    }

    public Timestamp getFirstCreated() {
        return firstCreated;
    }

    public void setFirstCreated(Timestamp firstCreated) {
        this.firstCreated = firstCreated;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public void AddFile(File file) {
        this.files.put(file.getName(), file);
    }

    public void rename(String newName) {
        this.folderName = newName;
    }


    // Files
    public HashMap<String, File> getFiles() {
        return files;
    }

    public void setFiles(HashMap<String, File> files) {
        this.files = files;
    }

    public void deleteFile(String fileName) {
        files.remove(fileName);
    }

    public void renameFile(String oldName, String newName) {
        File file = files.get(oldName);
        files.remove(oldName);
        files.put(newName, file);
    }


    // File Elements
    public void addNoteElement(String content, String fileName) {
        File file = files.get(fileName);
        FileElement element = new NoteElement(content);
        if (file != null) {
            file.addElement(element);
        }
    }

    public void addTodoElement(String content, String fileName) {
        File file = files.get(fileName);
        FileElement element = new TodoElement(content);
        if (file != null) {
            file.addElement(element);
        }
    }

    public void deleteElement(int index, String fileName) {
        File file = files.get(fileName);
        if (file != null) {
            file.deleteElement(index);
        }
    }

    public void updateElement(int index, String newContent, String fileName) {
        File file = files.get(fileName);
        if (file != null) {
            file.updateElement(index, newContent);
        }
    }
}










