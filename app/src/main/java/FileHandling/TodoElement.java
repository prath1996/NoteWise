package FileHandling;

import java.sql.Timestamp;

public class TodoElement implements FileElement {

    private String id;
    private String text;
    private boolean completed;
    private Timestamp lastModified;

    TodoElement(String text) {
        this.text = text;
    }

    public TodoElement() {
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
        return completed;
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.lastModified = new Timestamp(System.currentTimeMillis());
    }

}
