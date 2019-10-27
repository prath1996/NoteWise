package FileHandling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {
    private static FileManager fmInstance;

    private HashMap<String, Folder> folderDict;
    private DBHandler dbHandler;
    private ContextInfo contextInfo;

    private class ContextInfo {
        String folderID;
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

    public void updateContext(String folderID, String fileName) {
        if (folderID.equals("") && fileName.equals("")) {
            return;
        }
        if (!folderID.equals("")) {
            contextInfo.folderID = folderID;
        }
        if (!fileName.equals("")) {
            contextInfo.fileName = fileName;
        }
    }


    // Folders
    public Folder createFolder(String folderName) {
        if (getFolder(folderName, true) == null) {
            Folder folder = new Folder(folderName);
            dbHandler.createFolder(folder);
            folderDict.put(folder.getID(), folder);
            return folder;
        }
        return null;
    }


    public Folder getFolder(String nameOrID, boolean byName) {
        if (byName) {
            for (Map.Entry<String, Folder> entry : folderDict.entrySet()) {
                if (entry.getValue().getName().equals(nameOrID)) {
                    return entry.getValue();
                }
            }
            return null;
        } else {
            return folderDict.get(nameOrID);
        }
    }

    public void deleteFolder() throws Exception{
        try {
            String folderID = contextInfo.folderID;
            dbHandler.deleteFolder(folderID);
            folderDict.remove(folderID);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    public void renameFolder(String newName) throws Exception{
        Folder folder = getFolder(contextInfo.folderID, false);
        if (newName.equals(folder.getName())) {
            return;
        }
        if (getFolder(newName, true) == null) {
            folder.rename(newName);
            dbHandler.addToUpdate(contextInfo.folderID);
            dbHandler.update();
            return;
        }
        throw new Exception("Folder name already exists");
    }

    public HashMap<String, Folder> getAllFolders() {
        return folderDict;
    }



    // Files
    public void addNoteFile(String fileName) throws Exception{
        Folder folder = getFolder(contextInfo.folderID, false);
        if (folder != null) {
            File file = new NoteFile(fileName);
            folder.addFile(file);
            dbHandler.addToUpdate(contextInfo.folderID);
            dbHandler.update();
            return;
        }
        throw new Exception("Folder not found");
    }

    public void addTodoFile(String fileName) throws Exception {
        Folder folder = getFolder(contextInfo.folderID, false);
        if (folder != null) {
            File file = new TodoFile(fileName);
            folder.addFile(file);
            dbHandler.addToUpdate(contextInfo.folderID);
            dbHandler.update();
            return;
        }
        throw new Exception("File name already exists");
    }
    
    public void deleteFile(String fileName) throws Exception{
        Folder folder = getFolder(contextInfo.folderID, false);
        if (folder != null) {
            folder.deleteFile(fileName);
            dbHandler.addToUpdate(contextInfo.folderID);
            dbHandler.update();
            return;
        }
        throw new Exception("File name already exists");
    }

    public void renameFile(String oldName, String newName) throws Exception {
        Folder folder = getFolder(contextInfo.folderID, false);
        if (folder != null) {
            if (oldName.equals(newName)) {
                return;
            }
            folder.renameFile(oldName, newName);
            dbHandler.addToUpdate(contextInfo.folderID);
            dbHandler.update();
            return;
        }
        throw new Exception("File name already exists");
    }

    public List<File> getAllFiles() {
        Folder folder = getFolder(contextInfo.folderID, false);
        return folder.getFiles();
    }

    public List<File> getAllFiles(String folderID) {
        Folder folder = getFolder(folderID, false);
        return folder.getFiles();
    }




    // File Elements
    public void addNoteElement(String content) {
        Folder folder = getFolder(contextInfo.folderID, false);
        folder.addNoteElement(content, contextInfo.fileName);
        dbHandler.addToUpdate(contextInfo.folderID);
        dbHandler.update();
    }

    public void addTodoElement(String content) {
        Folder folder = getFolder(contextInfo.folderID, false);
        folder.addTodoElement(content, contextInfo.fileName);
        dbHandler.addToUpdate(contextInfo.folderID);
        dbHandler.update();
    }

    public void removeElement(int index) {
        Folder folder = getFolder(contextInfo.folderID, false);
        folder.deleteElement(index, contextInfo.fileName);
    }

    public void updateFileElement(int index, String newContent) {
        Folder folder = getFolder(contextInfo.folderID, false);
        folder.updateElement(index, newContent, contextInfo.fileName);
        dbHandler.addToUpdate(contextInfo.folderID);
        dbHandler.update();
    }



}









