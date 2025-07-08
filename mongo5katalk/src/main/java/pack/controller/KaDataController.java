package pack.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pack.model.KaData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class KaDataController {
    @Autowired
    private MongoClient mongoClient;

    @GetMapping("/")
    public String sijak() {
        return "index";
    }

    @GetMapping("/show")
    public String showData(Model model) {
        List<KaData> kadataList = new ArrayList<>();

        // MongoDB의 GridBucket에서 자료 읽기
        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoClient.getDatabase("katalkDB"), "katalkFiles");

        try {
            // GridFSBucket에서 저장된 자료를 하나씩 가져오기
            for (GridFSFile gridFSFile : gridFSBucket.find()){
                ObjectId fileId = gridFSFile.getObjectId();

                // file download
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                gridFSBucket.downloadToStream(fileId, outputStream);
                String fileContent = new String(outputStream.toByteArray());

                // JSON 파싱
                if(fileContent.trim().startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(fileContent);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        KaData kaData = new KaData();
                        kaData.setReq(jsonObject.getString("req"));
                        kaData.setRes(jsonObject.getString("res"));
                        kadataList.add(kaData);
                    }
                } else {
                    JSONObject jsonObject = new JSONObject(fileContent);
                    KaData kaData = new KaData();
                    kaData.setReq(jsonObject.getString("req"));
                    kaData.setRes(jsonObject.getString("res"));
                    kadataList.add(kaData);
                }

            }
        } catch (Exception e) {
            System.out.println("err : " + e.getMessage());
        }

        model.addAttribute("dataList", kadataList);
        return "show";
    }

}
