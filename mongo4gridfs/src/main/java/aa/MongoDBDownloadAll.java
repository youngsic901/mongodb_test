package aa;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class MongoDBDownloadAll {
    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("katalkDB");

            GridFSBucket gridFSBucket = GridFSBuckets.create(database, "katalkFiles");

            MongoCursor<GridFSFile> cursor = gridFSBucket.find().iterator();

            while( cursor.hasNext() ) {
                GridFSFile gridFsFile = cursor.next();
                ObjectId field = gridFsFile.getObjectId(); // 파일의 ObjedtId를 추출

                // 파일 다운로드(읽기)
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    // 지정한 getObjectId나 파일 이름으로 저장된 파일을 다운로드 후 outputStream에 저장
                    gridFSBucket.downloadToStream(field, outputStream);

                    String fileContent = new String(outputStream.toByteArray());

                    // 다운로드한 파일을 바이트 배열에서 문자열로 변환
                    // System.out.println("File content for ObjectId : " + field.toHexString() + " : " + fileContent);

                    // String을 JSON 데이터로 parsing
                    if(fileContent.trim().startsWith("[")) {
                        // 파일 내용이 JSON 배열이라면 대괄호로 시작될 것이므로 배열로 처리
                        JSONArray jsonArray = new JSONArray(fileContent);

                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i); // 각 요소 데이터를 JSON 객체로 변환
                            String req = jsonObject.getString("req");
                            String res = jsonObject.getString("res");
                            System.out.println("질문 : " + req + ", 답변 : " + res);
                        }
                    } else { // 단일 JSON 객체일 경우
                        JSONObject jsonObject = new JSONObject(fileContent);
                        String req = jsonObject.getString("req");
                        String res = jsonObject.getString("res");
                        System.out.println("질문 : " + req + ", 답변 : " + res);
                    }
                } catch (Exception e) {
                    System.out.println("파일 찾기 실패 : ObjectId => " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("err : " + e.getMessage());
        }
    }
}
