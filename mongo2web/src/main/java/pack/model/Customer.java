package pack.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
@Getter
@Setter
public class Customer {
    @Id
    private String id;
    private String name;
    private int age;
    private String gender;

    @Override
    public String toString() {
        return "Customer{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", age=" + age + ", gender='" + gender +'\'' + '}';
    }
}
