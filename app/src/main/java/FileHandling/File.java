package FileHandling;

import java.sql.Timestamp;
import java.util.List;

public abstract class File {
    private String name;
    private Timestamp lastModified;
    private int uniqueID;
    private List<FileElement> elements;

    File(String name) {
        this.name = name;
        this.lastModified = new Timestamp(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    Timestamp getLastModified() {
        return lastModified;
    }

    void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    void setID(int id) {
        this.uniqueID = id;
    }

    int getID() {
        return uniqueID;
    }

    List getElements() {
        return elements;
    }

    void addElement(FileElement Element) {
        this.elements.add(Element);
    }

    void deleteElement(int index) {
        this.elements.remove(index);
    }

    void updateElement(int index, String newContent) {
        this.elements.get(index).setText(newContent);
    }

    abstract int getType();
}
