package FileHandling;

import java.sql.Timestamp;

interface FileElement {
    String getID();

    void setID(String id);

    String getText();

    void setText(String text);

    Timestamp getLastModified();

    boolean isCompleted();

    void setCompleted(boolean completed);
}
