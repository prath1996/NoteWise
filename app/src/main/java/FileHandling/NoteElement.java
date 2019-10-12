package FileHandling;

import java.sql.Timestamp;

public class NoteElement implements FileElement {

    private String id;
    private String text;
    private Timestamp lastModified;

    NoteElement(String text) {
        this.text = text;
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
        return false;
    }

    @Override
    public void setCompleted(boolean completed) {}

}
