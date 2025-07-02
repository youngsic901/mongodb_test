package mongo1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Map;

// MongoDB의 불특정 칼럼을 자바에서 처리할 때 명시적으로 칼럼을 선택하기에는 곤란함
// 모든 키와 값을 동적으로 처리 가능
public class MongoTest3 {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        try(MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("test"); // DB 연결
            MongoCollection<Document> collection = db.getCollection("customer");

            // MongoDB 컬렉션에서 각 Document를 가져오기 반복
            for(Document doc : collection.find()) {
                // 각 Document 내의 필드(Key-Value)를 반복
                // doc.entrySet()은 Document 객체 내의 모든 필드(Key-Value)를 반환
                for(Map.Entry<String, Object> entry : doc.entrySet()) {
                    String fieldName = entry.getKey();
                    Object fieldValue = entry.getValue();
                    System.out.println(fieldName + " : " + fieldValue);
                }
                System.out.println("---");
            }

            // 총 문서 수
            System.out.println("자료 건수 : " + collection.countDocuments() );
        } catch (Exception e) {
            System.out.println("err : " + e);
        }
    }
}
