package com.example.notewise;

import java.util.HashMap;

public class FileManager {
    private static FileManager fmInstance;

    private HashMap<Long, Folder> folderDict;
    private DBHandler dbHandler;
    private ContextInfo contextInfo;

    private class ContextInfo {
        long folderID;
        String fileName;
        int fileElementIndex;
    }

    private FileManager() {
        dbHandler = DBHandler.getInstance();
        folderDict = dbHandler.getAllData();
        contextInfo = new ContextInfo();
    }

    public Folder getFolder(long id) {
        return folderDict.get(id);
    }

    public static FileManager getInstance() {
        if (fmInstance == null) {
            fmInstance = new FileManager();
        }
        return fmInstance;
    }

    public void updateContext(long folderID, String fileName, int fileElementIndex) {
        contextInfo.folderID = folderID;
        contextInfo.fileName = fileName;
        contextInfo.fileElementIndex = fileElementIndex;
    }




    // Folders
    public Folder createFolder(String folderName) {
        Folder folder = new Folder(folderName);
        dbHandler.createFolder(folder);
        return folder;
    }

    public void deleteFolder(long folderID) {
        dbHandler.deleteFolder(folderID);
        folderDict.remove(folderID);
    }

    public void renameFolder(long folderID, String newName) {
        Folder folder = getFolder(folderID);
        folder.rename(newName);
        dbHandler.addToUpdate(contextInfo.folderID);
    }

    public HashMap<Long, Folder> getAllFolders() {
        return folderDict;
    }



    // Files
    public void createNoteFile(String fileName) {
        Folder folder = getFolder(contextInfo.folderID);
        File file = new NoteFile(fileName);
        folder.AddFile(file);
        dbHandler.addToUpdate(contextInfo.folderID);
    }

    public void createTodoFile(String fileName) {
        Folder folder = getFolder(contextInfo.folderID);
        File file = new TodoFile(fileName);
        folder.AddFile(file);
        dbHandler.addToUpdate(contextInfo.folderID);
    }
    
    public void deleteFile(String fileName) {
        Folder folder = getFolder(contextInfo.folderID);
        folder.deleteFile(fileName);
        dbHandler.addToUpdate(contextInfo.folderID);
    }

    public void renameFile(String oldName, String newName) {
        Folder folder = getFolder(contextInfo.folderID);
        folder.renameFile(oldName, newName);
        dbHandler.addToUpdate(contextInfo.folderID);
    }

    public HashMap<String, File> getAllFiles() {
        Folder folder = getFolder(contextInfo.folderID);
        return folder.getFiles();
    }




    // File Elements
    public void addNoteElement(String content) {
        Folder folder = getFolder(contextInfo.folderID);
        folder.addNoteElement(content, contextInfo.fileName);
        dbHandler.addToUpdate(contextInfo.folderID);
    }

    public void addTodoElement(String content) {
        Folder folder = getFolder(contextInfo.folderID);
        folder.addTodoElement(content, contextInfo.fileName);
        dbHandler.addToUpdate(contextInfo.folderID);
    }

    public void updateFileElement(int index, String newContent) {
        Folder folder = getFolder(contextInfo.folderID);
        folder.updateElement(index, newContent, contextInfo.fileName);
        dbHandler.addToUpdate(contextInfo.folderID);
    }


}









