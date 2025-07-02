package pack.model;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void printAllCustomers() { // 모든 고객 출력
        customerRepository.findAll().forEach(customer -> { System.out.println(customer); });
    }

    public void insertCustomer(String name, int age, String gender) {
        Customer existsCustomer = customerRepository.findByName(name);

        if(existsCustomer == null) {
            Customer newCustomer = new Customer();
            newCustomer.setName(name);
            newCustomer.setAge(age);
            newCustomer.setGender(gender);

            customerRepository.save(newCustomer);
        } else {
            System.out.println("이미 동일한 이름의 고객이 있습니다.");
        }

    }

    public void updateCustomer(String name, int age, String gender) {
        Customer existsCustomer = customerRepository.findByName(name);

        if(existsCustomer != null) { // age, gender 수정
            existsCustomer.setAge(age);
            existsCustomer.setGender(gender);

            customerRepository.save(existsCustomer);
        } else {
            System.out.println("수정 자료를 찾을 수 없습니다.");
        }
    }

    public void deleteCustomer(String name) { // 고객 삭제
        Customer existsCustomer = customerRepository.findByName(name);

        if(existsCustomer != null) {
            customerRepository.delete(existsCustomer);
        } else {
            System.out.println("삭제 자료를 찾을 수 없습니다.");
        }
    }
}
