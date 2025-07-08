package pack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "katalkFiles")
public class KaData {
    @Id
    private String id;
    private String req; // 질문
    private String res; // 답변
}
