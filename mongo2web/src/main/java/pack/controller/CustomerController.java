package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pack.model.CustomerService;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/alldata")
    public String allCustomers() {
        customerService.printAllCustomers();
        return "전체자료 출력";
    }

    @GetMapping("/insert")
//    @PostMapping("/insert")
    public String insCustomer(@RequestParam(name = "name")String name,
                              @RequestParam(name = "age")int age,
                              @RequestParam(name = "gender")String gender) {
        customerService.insertCustomer(name, age, gender);
        return "자료 추가";
    }
}
