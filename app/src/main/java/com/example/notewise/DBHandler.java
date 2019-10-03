package com.example.notewise;

// Base Stitch Packages
import android.os.Debug;
import android.util.Log;

import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;

// Packages needed to interact with MongoDB and Stitch
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

// Necessary component for working with MongoDB Mobile
import com.mongodb.stitch.android.services.mongodb.local.LocalMongoDbService;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.Console;
import java.sql.Timestamp;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;

public class DBHandler {
    private static DBHandler instance;

    private StitchAppClient stitchAppClient;
    private MongoClient mobileClient;

    private final String DB_NAME = "noteWise_db";
    private final String FOLDER_COLLECTION = "folder_coll";
    private final String FILE_COLLECTION = "file_coll";

    private final String ID = "_ID";
    private final String LAST_MODIFIED = "lmod";
    private final String TEXT_CONTENT = "txt";

    private final String DB_MOD_ERROR = "dberr";

    private MongoDatabase mongoDB;
    private MongoCollection<Document> folderCollection;
    private MongoCollection<Document> fileCollection;

    public static DBHandler getInstance() {
        if (instance == null) {
            instance = new DBHandler();
        }
        return instance;
    }

    private DBHandler() {
        // Create the default Stitch Client
        stitchAppClient = Stitch.initializeDefaultAppClient("<APP ID>");

        // Create a Client for MongoDB Mobile (initializing MongoDB Mobile)
        mobileClient = stitchAppClient.getServiceClient(LocalMongoDbService.clientFactory);

        // Get DB, create if non existent
        mongoDB = mobileClient.getDatabase(DB_NAME);

        // Populate folder collection
        if (!collectionExists(FOLDER_COLLECTION)) {
            mongoDB.createCollection(FOLDER_COLLECTION);
        }
        folderCollection = mongoDB.getCollection(FOLDER_COLLECTION);

        // Populate file collection
        if (!collectionExists(FILE_COLLECTION)) {
            mongoDB.createCollection(FILE_COLLECTION);
        }
        fileCollection = mongoDB.getCollection(FILE_COLLECTION);
    }

    private boolean collectionExists(final String collectionName) {
        MongoIterable<String> collectionNames = mongoDB.listCollectionNames();
        for (final String name : collectionNames) {
            if (name.equalsIgnoreCase(collectionName)) {
                return true;
            }
        }
        return false;
    }

    public void createFileElement(FileElement element) {
        Document doc = new Document();
        doc.put(TEXT_CONTENT, element.getText());
        doc.put(LAST_MODIFIED, element.getLastModified());
        insert(doc);
        element.setID((String)doc.get(ID));
    }

    public void updateFileElement(FileElement element) {
        Document doc = new Document();
        Bson filter = eq(ID, element.getID());
        Bson query = combine(set(TEXT_CONTENT, element.getText()), set(LAST_MODIFIED, element.getLastModified()));
        update(filter, query);
    }

    private void insert(Document document) {
        try {
            fileCollection.insertOne(document);
        } catch (MongoException e) {
            Log.println(1, DB_MOD_ERROR, e.getMessage());
        }
    }

    private void update(Bson filter, Bson updateQuery) {
        try {
            fileCollection.updateOne(filter, updateQuery);
        } catch (MongoException e) {
            Log.println(1, DB_MOD_ERROR, e.getMessage());
        }
    }

    public void deleteFolder(Bson filter) {
        try {
            fileCollection.deleteOne(filter);
        } catch (MongoException e) {
            Log.println(1, DB_MOD_ERROR, e.getMessage());
        }
    }

}
