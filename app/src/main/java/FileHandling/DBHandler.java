package FileHandling;

import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import io.paperdb.Paper;

public class DBHandler {

    private static DBHandler instance;
    private static final int IDlength = 5;

    private final String DB_MOD_ERROR = "dberr";
    private HashSet<String> updateKeys;


    static DBHandler getInstance() {
        if (instance == null) {
            instance = new DBHandler();
        }
        return instance;
    }

    private DBHandler() {
        Paper.book().destroy();
        updateKeys = new HashSet<>();
    }

    private String createFolderID() {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(IDlength);
        do {
            for (int i = 0; i < IDlength; i++) {
                int index = (int)(alphaNumericString.length() * Math.random());
                sb.append(alphaNumericString.charAt(index));
            }
        } while (Paper.book().contains(sb.toString()));

        return sb.toString();
    }

    void createFolder(Folder folder) {
        try {
            folder.setID(createFolderID());
            Paper.book().write(folder.getID(), folder);
        } catch (Exception e) {
            Log.e(DB_MOD_ERROR, e.getMessage());
        }
    }

    void addToUpdate(String folderID) {
        updateKeys.add(folderID);
    }

    void deleteFolder(String folderID) {
        try {
            Paper.book().delete("folderID");
        } catch (Exception e) {
            Log.e(DB_MOD_ERROR, e.getMessage());
        }
    }

    HashMap<String, Folder> getAllData() {
        HashMap<String, Folder> folderMap = new HashMap<>();
        List<String> allKeys = Paper.book().getAllKeys();
        if (allKeys.size() > 0) {
            for (String key : allKeys) {
                folderMap.put(key, Paper.book().read(key));
            }
        }
        return folderMap;
    }

    void update() {
        if (updateKeys.isEmpty()) {
            return;
        }
        for (String folderID:updateKeys) {
            Folder folder = FileManager.getInstance().getFolder(folderID, false);
            Paper.book().write(folder.getID(), folder);
        }
    }
}
