package FileHandling;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Folder {

    private String ID;

    private String folderName;
    private Timestamp firstCreated;
    private Timestamp lastModified;
    private List<File> files;

    private File getFile(String fileName) {
        for(File file: files) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    Folder(String name) {
        this.folderName = name;
        this.firstCreated = new Timestamp(System.currentTimeMillis());
        this.lastModified = this.firstCreated;
    }


    // Folder
    public String getID() {
        return ID;
    }

    void setID(String id) {
        this.ID = id;
    }

    public String getName() {
        return folderName;
    }

    void setName(String folderName) {
        this.folderName = folderName;
    }

    Timestamp getFirstCreated() {
        return firstCreated;
    }

    void setFirstCreated(Timestamp firstCreated) {
        this.firstCreated = firstCreated;
    }

    Timestamp getLastModified() {
        return lastModified;
    }

    void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    void rename(String newName) {
        this.folderName = newName;
    }


    // Files
    void sortByDate() {
//        files.sort((o1, o2) -> o1.getLastModified().compareTo(o2.getLastModified()));
    }

    void sortByName() {

    }

    List<File> getFiles() {
        return files;
    }

    void addFile(File file) {
        if (files == null) {
            files = new ArrayList<>();
        }
        for (File f : files) {
            if (f.getName().equals(file.getName())) {
                return;
            }
        }
        this.files.add(file);
    }

//    public void setFiles(TreeMap<String, File> files) {
//        this.files = files;
//    }

    void deleteFile(String fileName) {
        File file = getFile(fileName);
        if (file != null) {
            files.remove(file);
        }
    }

    void renameFile(String oldName, String newName) {
        File file = getFile(oldName);
        if (file != null) {
            file.setName(newName);
        }
    }


    // File Elements
    void addNoteElement(String content, String fileName) {
        File file = getFile(fileName);
        if (file != null) {
            FileElement element = new NoteElement(content);
            file.addElement(element);
        }
    }

    void addTodoElement(String content, String fileName) {
        File file = getFile(fileName);
        if (file != null) {
            FileElement element = new TodoElement(content);
            file.addElement(element);
        }
    }

    void deleteElement(int index, String fileName) {
        File file = getFile(fileName);
        if (file != null) {
            file.deleteElement(index);
        }
    }

    void updateElement(int index, String newContent, String fileName) {
        File file = getFile(fileName);
        if (file != null) {
            file.updateElement(index, newContent);
        }
    }
}










