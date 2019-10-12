package FileHandling;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {
    private static FileManager fmInstance;

    private HashMap<Long, Folder> folderDict;
    private DBHandler dbHandler;
    private ContextInfo contextInfo;

    private class ContextInfo {
        long folderID;
        String fileName;
    }

    private FileManager() {
        dbHandler = DBHandler.getInstance();
        folderDict = dbHandler.getAllData();
        contextInfo = new ContextInfo();
    }

    public static FileManager getInstance() {
        if (fmInstance == null) {
            fmInstance = new FileManager();
        }
        return fmInstance;
    }

    public void updateContext(long folderID) {
        Log.e("notewise", "context update to " + String.valueOf(folderID));
        contextInfo.folderID = folderID;
    }

    public void updateContext(String fileName) {
        contextInfo.fileName = fileName;
    }

    public void updateContext(long folderID, String fileName) {
        contextInfo.folderID = folderID;
        contextInfo.fileName = fileName;
    }




    // Folders
    public Folder createFolder(String folderName) {
        if (getFolder(folderName) == null) {
            Folder folder = new Folder(folderName);
            dbHandler.createFolder(folder);
            folderDict.put(folder.getID(), folder);
            return folder;
        }
        return null;
    }

    public Folder getFolder(long id) {
        return folderDict.get(id);
    }

    public Folder getFolder(String folderName) {
        for (Map.Entry<Long, Folder>entry : folderDict.entrySet()) {
            if (entry.getValue().getName().equals(folderName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void deleteFolder(long folderID) {
        dbHandler.deleteFolder(folderID);
        folderDict.remove(folderID);
    }

    public void renameFolder(long folderID, String newName) {
        Folder folder = getFolder(folderID);
        folder.rename(newName);
        dbHandler.addToUpdate(contextInfo.folderID);
        dbHandler.update();
    }

    public HashMap<Long, Folder> getAllFolders() {
        return folderDict;
    }



    // Files
    public void addNoteFile(String fileName) {
        Folder folder = getFolder(contextInfo.folderID);
        if (folder != null) {
            File file = new NoteFile(fileName);
            folder.addFile(file);
            dbHandler.addToUpdate(contextInfo.folderID);
            dbHandler.update();
        }
    }

    public void addTodoFile(String fileName) {
        Folder folder = getFolder(contextInfo.folderID);
        if (folder != null) {
            File file = new TodoFile(fileName);
            folder.addFile(file);
            dbHandler.addToUpdate(contextInfo.folderID);
            dbHandler.update();
        }
    }
    
    public void deleteFile(String fileName) {
        Folder folder = getFolder(contextInfo.folderID);
        if (folder != null) {
            folder.deleteFile(fileName);
            dbHandler.addToUpdate(contextInfo.folderID);
            dbHandler.update();
        }
    }

    public void renameFile(String oldName, String newName) {
        Folder folder = getFolder(contextInfo.folderID);
        if (folder != null) {
            folder.renameFile(oldName, newName);
            dbHandler.addToUpdate(contextInfo.folderID);
            dbHandler.update();
        }
    }

    public List<File> getAllFiles() {
        Folder folder = getFolder(contextInfo.folderID);
        return folder.getFiles();
    }

    public List<File> getAllFiles(long folderID) {
        Folder folder = getFolder(folderID);
        return folder.getFiles();
    }




    // File Elements
    public void addNoteElement(String content) {
        Folder folder = getFolder(contextInfo.folderID);
        folder.addNoteElement(content, contextInfo.fileName);
        dbHandler.addToUpdate(contextInfo.folderID);
        dbHandler.update();
    }

    public void addTodoElement(String content) {
        Folder folder = getFolder(contextInfo.folderID);
        folder.addTodoElement(content, contextInfo.fileName);
        dbHandler.addToUpdate(contextInfo.folderID);
        dbHandler.update();
    }

    public void updateFileElement(int index, String newContent) {
        Folder folder = getFolder(contextInfo.folderID);
        folder.updateElement(index, newContent, contextInfo.fileName);
        dbHandler.addToUpdate(contextInfo.folderID);
        dbHandler.update();
    }


}








