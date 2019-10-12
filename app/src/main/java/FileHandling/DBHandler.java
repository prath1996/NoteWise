package FileHandling;

import android.util.Log;

import com.noodle.Noodle;
import com.noodle.collection.Collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DBHandler {
    private static DBHandler instance;

    private Noodle noodleInstance;

    private final String DB_MOD_ERROR = "dberr";

    private HashSet<Long> updateKeys;

    private Collection<Folder> folderCollection;

    public static void init(Collection<Folder> folderCollection) {
        instance = new DBHandler(folderCollection);
//        folderCollection.clear();
    }

    public static DBHandler getInstance() {
        return instance;
    }

    private DBHandler(Collection<Folder> folderCollection) {
        updateKeys = new HashSet<>();
        this.folderCollection = folderCollection;
    }

    public void createFolder(Folder folder) {
        try {
            folderCollection.put(folder);
        } catch (Exception e) {
            Log.e(DB_MOD_ERROR, e.getMessage());
        }
    }

    public void addToUpdate(long folderID) {
        updateKeys.add(folderID);
    }

    public void deleteFolder(long folderID) {
        try {
            folderCollection.delete(folderID);
        } catch (Exception e) {
            Log.e(DB_MOD_ERROR, e.getMessage());
        }
    }

    public HashMap<Long, Folder> getAllData() {
        HashMap<Long, Folder> folderMap = new HashMap<>();
        for (Folder folder:this.folderCollection.getAll()) {
            folderMap.put(folder.getID(), folder);
        }
        return folderMap;
    }

    public void update() {
        for (Long folderID:updateKeys) {
            Folder folder = FileManager.getInstance().getFolder(folderID);
            folderCollection.put(folder);
        }
    }
}