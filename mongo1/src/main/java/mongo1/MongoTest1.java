package mongo1;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoTest1 {
    public static void main(String[] args) {
        // MongoDB 연동
        MongoClient client = MongoClients.create("mongodb://localhost:27017"); // MongoDB 연결 객체 생성
        try {
            MongoDatabase db = client.getDatabase("test"); // DB 연결
            MongoCollection<Document> collection = db.getCollection("customer"); // collection 연결
            System.out.println("자료 건수 : " + collection.countDocuments());

            // 한 개의 document(레코드)를 불러오기
            Document document = collection.find().first();
            System.out.println("첫번째 자료 : " + document.toJson());

            // 여러 개(전체)의 자료 읽기
            // FindIterable<Document> iter = collection.find();
            // MongoCursor<Document> cursor = iter.iterator();
            MongoCursor<Document> cursor = collection.find().iterator();
            // MongoCursor<Document> cursor = collection.find().limit(3).iterator(); // 3개만 읽기

            while(cursor.hasNext()) {
//				Document doc = cursor.next();
//				String jsonResult = doc.toJson();
                String jsonResult = cursor.next().toJson();
                System.out.println(jsonResult);
            }

            System.out.println("이름 나이 성별 출력---------------------------");
            cursor = collection.find().iterator();
            while(cursor.hasNext()) {
                Document doc2 = cursor.next();
                System.out.println("이름 : " + doc2.get("name") +
                        ", 나이 : " + doc2.get("age") +
                        ", 성별 : " + doc2.get("gender"));
            }
        } catch (Exception e) {
            System.out.println("에러 : " + e.getMessage());
        } finally {
            client.close();
        }
    }
}
