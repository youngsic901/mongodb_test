package aa;

import com.mongodb.MongoGridFSException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class MongoDbDownload { // MongoDB 자료 일부 읽기
    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            GridFSBucket gridFSBucket = GridFSBuckets.create(mongoClient.getDatabase("katalkDB"), "katalkFiles");

            // ObjectId 배열
            ObjectId[] fields = {
                new ObjectId("68673951d3e4f953324de22f"),
                new ObjectId("68673951d3e4f953324de231"),
                new ObjectId("68673951d3e4f953324de235"),
            };

            for(ObjectId field : fields) {
                downloadAndPrintFileContent(gridFSBucket, field);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.getMessage());
        }
    }

    // 파일을 다운로드(읽기)하고 내용 출력용 메소드
    private static void downloadAndPrintFileContent(GridFSBucket gridFSBucket, ObjectId field) {
        try {
            // 다운로드한 파일 내용을 저장하기 위한 ByteArrayOutputStream 객체 생성
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            gridFSBucket.downloadToStream(field, outputStream);

            String fileContent = new String(outputStream.toByteArray());

            System.out.println("File content for ObjectId : " + field + " -> " + fileContent);

            // JSON 데이터 파싱
            JSONObject jsonObject = new JSONObject(fileContent);
            String req = jsonObject.getString("req");
            String res = jsonObject.getString("res");
            System.out.println("Request : " + req + " | Response : " + res);
        } catch (MongoGridFSException e) {
            System.out.println("해당 파일을 찾을 수 없습니다 : " + field.toHexString());
        } catch (Exception e) {
            System.out.println("처리 오류 : " + e);
        }
    }
}
