package com.example.csseticketingmobileapp.network;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    //MongoDB connection URI
    private static final String MONGODB_URI = "mongodb+srv://CSSE-60:csse60@cluster0.b83ayll.mongodb.net/csse";

    public static MongoDatabase getDatabase() {
        MongoClient mongoClient = MongoClients.create(MONGODB_URI);
        return mongoClient.getDatabase("csse");
    }
}



