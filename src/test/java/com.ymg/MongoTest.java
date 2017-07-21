package com.ymg;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.ymg.framework.mongodb.MongoDBUtil;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.config.AopNamespaceHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumg on 2017/7/18.
 */
public class MongoTest {

    private MongoDatabase mongoDatabase;

    @Before
    public void setup(){
        mongoDatabase = MongoDBUtil.getMongoDatabase();
    }

    /**
     * 创建集合
     */
    @Test
    public void testCreateCollection(){
        mongoDatabase.createCollection("words");
    }

    @Test
    public void testGetCollection(){
        MongoCollection<Document> words = mongoDatabase.getCollection("words");
        System.out.println("集合选择成功");
    }

    @Test
    public void testInsertCollection(){
        Document document1 = new Document("name","yumeiguang").append("age1",20)
                .append("hobby","basketball").append("eat","rice");
        Document document2 = new Document("name","yumg").append("age",20)
                .append("hobby","basketball").append("eat","rice");

        MongoCollection<Document> words = mongoDatabase.getCollection("words");
        System.out.println(words);
        List<Document> documentList = new ArrayList<Document>();
        documentList.add(document1);
        documentList.add(document2);

        words.insertMany(documentList);

    }

    /**
     * 查询文档
     */
    @Test
    public void testSearchAllCollection(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("words");

        //检索所有文档
        /**
         * 1. 获取迭代器FindIterable<Document>
         * 2. 获取游标MongoCursor<Document>
         * 3. 通过游标遍历检索出的文档集合
         * */
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }

    }

    /**
     * 更新文档
     */
    @Test
    public void testUpdateCollection(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("words1");
        collection.updateMany(Filters.eq("age",20),new Document("$set",new Document("age",29)));
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

    /**
     * 删除某个文档
     */
    @Test
    public void testDeleteCollection(){
        MongoCollection<Document> collection = mongoDatabase.getCollection("words");

        collection.deleteOne(Filters.eq("age1",29));
        collection.deleteMany(Filters.eq("name","yumeiguang"));
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }

    }
}
