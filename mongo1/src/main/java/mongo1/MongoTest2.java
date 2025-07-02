package mongo1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoTest2 {
    // Customer 컬렉션 데이터를 저장하는 클래스
    static class Customer {
        private ObjectId id;
        private String name;
        private int age;
        private String gender;

        public Customer(ObjectId id, String name, int age, String gender) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender;
        }
    }
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        try(MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("test"); // DB 연결
            MongoCollection<Document> collection = db.getCollection("customer");

            // 자료 추가
//            Document newDocument = new Document("name", "신기해").append("age", 30).append("gender", "남성");
//            collection.insertOne(newDocument); // 1개의 자료 추가

            // 특정 자료 찾기(이름이 '신기해'인 자료 검색 후 수정 또는 삭제 수행
            Document foundCustomer = collection.find(eq("name", "신기해")).first();

            if(foundCustomer != null) {
            ObjectId objectId = foundCustomer.getObjectId("_id"); // 검색된 document의 id 얻기

                // 자료 수정
//                collection.updateOne(eq("_id", objectId),
//                            Updates.combine(Updates.set("age", 44), Updates.set("gender", "여성")));
                // 자료 삭제
                collection.deleteOne(eq("_id", objectId));
            }


            List<Customer> clist = new ArrayList<>();
            for(Document doc : collection.find()) {
                // age 필드가 Integer 또는 String일 수 있으므로 적절히 처리
                Object ageObj = doc.get("age");
                int age = 0;
                if (ageObj instanceof Integer) {
                    age = (Integer) ageObj;
                } else if (ageObj instanceof String) {
                    try {
                        age = Integer.parseInt((String) ageObj);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid age format : " + ageObj);
                    }
                }

                Customer customer = new Customer(doc.getObjectId("_id"), doc.getString("name"), age, doc.getString("gender"));

                clist.add(customer);
            }

            // 자료 출력
            for(Customer customer : clist) {
//                System.out.println(customer);
                System.out.println(customer.id + " : " + customer.name + " : " + customer.age + " : " + customer.gender);
            }
        } catch (Exception e) {
            System.out.println("err : " + e);
        }
    }
}
