package pack.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// MongoDB와 상호 작용하기 위한 Repository 인터페이스
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    // 추가적인 쿼리 메소드 선언
//    Customer findByEmail(String name);
    Customer findByName(String name);
}
