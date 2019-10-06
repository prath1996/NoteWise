package com.example.notewise;

import com.noodle.Id;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class Folder {
    @Id
    long id;

    private String folderName;
    private Timestamp firstCreated;
    private Timestamp lastModified;
    private List<File> files;

    private File getFile(String fileName) {
        for(File file: files) {
            if (file.getName() == fileName) {
                return file;
            }
        }
        return null;
    }

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

    public void rename(String newName) {
        this.folderName = newName;
    }


    // Files
    public void sortByDate() {
//        files.sort((o1, o2) -> o1.getLastModified().compareTo(o2.getLastModified()));
    }

    public void sortByName() {

    }

    public List<File> getFiles() {
        return files;
    }

    public void addFile(File file) {
        if (files == null) {
            files = new ArrayList<>();
        }
        this.files.add(file);

    }

//    public void setFiles(TreeMap<String, File> files) {
//        this.files = files;
//    }

    public void deleteFile(String fileName) {
        files.remove(fileName);
    }

    public void renameFile(String oldName, String newName) {
        File file = getFile(oldName);
        if (file != null) {
            file.setName(newName);
        }
    }


    // File Elements
    public void addNoteElement(String content, String fileName) {
        File file = getFile(fileName);
        if (file != null) {
            FileElement element = new NoteElement(content);
            file.addElement(element);
        }
    }

    public void addTodoElement(String content, String fileName) {
        File file = getFile(fileName);
        if (file != null) {
            FileElement element = new TodoElement(content);
            file.addElement(element);
        }
    }

    public void deleteElement(int index, String fileName) {
        File file = getFile(fileName);
        if (file != null) {
            file.deleteElement(index);
        }
    }

    public void updateElement(int index, String newContent, String fileName) {
        File file = getFile(fileName);
        if (file != null) {
            file.updateElement(index, newContent);
        }
    }
}










