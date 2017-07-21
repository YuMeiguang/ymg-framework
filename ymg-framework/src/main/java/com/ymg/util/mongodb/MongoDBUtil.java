package com.ymg.util.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumg on 2017/7/14.
 */
public class MongoDBUtil {
    private static final String IP = "120.26.202.235";
    private static final Integer PORT = 27017;
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "yumg10";
    private static final String DATABASE_NAME = "blog";
//    private static final String DATABASE_NAME = "admin";

    private static MongoDatabase mongoDatabase = null;

    static {
        try{
            ServerAddress serverAddress = new ServerAddress(IP,PORT);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);

            MongoCredential credential = MongoCredential.createCredential(USER_NAME,DATABASE_NAME,PASSWORD.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            //通过连接认证获取MongoDB连接

            MongoClient mongoClient = new MongoClient(addrs,credentials);

            //连接到数据库
            mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
            System.out.println("Connect to database successfully");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Connect to database failed");
        }

    }

    /**
     * 获取MongoDB实例
     * @return
     */
    public synchronized static MongoDatabase getMongoDatabase(){
        return mongoDatabase;
    }
}
