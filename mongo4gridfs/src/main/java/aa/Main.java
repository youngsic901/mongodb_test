package aa;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017";

        try(MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("katalkDB"); // 없으면 생성함

            GridFSBucket gridFSBucket = GridFSBuckets.create(database, "katalkFiles");

            ClassLoader classLoader = MongoDbUpload.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("katalkdata.csv");

            if(inputStream != null) {
                uploadCsvToMongoDB(inputStream, gridFSBucket);
            } else {
                System.out.println("파일을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("err : " + e.getMessage());
        }
    }

    // CSV 파일을 MongoDB에 upload 처리 메소드
    private static void uploadCsvToMongoDB(InputStream inputStream, GridFSBucket gridFSBucket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            CSVReader csvReader = new CSVReader(reader)){
            List<String[]> records = csvReader.readAll(); // CSV 파일의 모든 행을 읽어 리스트로 저장

            // 각 행을 BSON Document로 변환하여 저장
            for(String[] record : records) {
                // 첫번째 필드를 "req"로, 두번째 필드를 "res"로 저장
                Document doc = new Document("req", record[0]).append("res", record[1]);

                // 저장 옵션 설정(chunk size 등), 1MB 크기의 정크로 나누어 저장(대용량 파일 세분해서 처리)
                GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(1024 * 1024);

                // MongoDB에 저장 (GridFS 사용)
                // BSON Document를 JSON 형식의 바이트 배열로 반환
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(doc.toJson().getBytes());
                ObjectId field = gridFSBucket.uploadFromStream("katalkdata", byteArrayInputStream, options);
                // upload가 완료되면 ObjectId를 반환

                System.out.println("Saved file with ID : " + field.toHexString());
            }
        } catch (IOException | CsvException e) {
            System.out.println("uploadCsvToMongoDB err : " + e.getMessage());
        }
    }
}