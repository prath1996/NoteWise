package com.example.notewise;

// Base Stitch Packages
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;

// Packages needed to interact with MongoDB and Stitch
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

// Necessary component for working with MongoDB Mobile
import com.mongodb.stitch.android.services.mongodb.local.LocalMongoDbService;

import java.util.Set;

public class DBHandler {
    private static DBHandler instance;

    private StitchAppClient stitchAppClient;
    private MongoClient mobileClient;

    private final String DB_NAME = "noteWise_db";
    private final String FOLDER_COLLECTION = "folder_coll";
    private final String FILE_COLLECTION = "file_coll";

    private MongoDatabase mongoDB;
    private MongoCollection folderCollection;
    private MongoCollection fileCollection;

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

    public void createTODO() {

    }

    public void createNote() {

    }

    public void insert() {

    }

    public void update() {

    }

    public void delete() {

    }

}