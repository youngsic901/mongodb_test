package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pack.model.Customer;
import pack.model.CustomerService;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/alldata")
    public List<Customer> allCustomers() {
        return customerService.printAllCustomers();
    }

//    @GetMapping("/insert")
    @PostMapping("/insert")
    public String insCustomer(@RequestParam(name = "name")String name,
                              @RequestParam(name = "age")int age,
                              @RequestParam(name = "gender")String gender) {
        customerService.insertCustomer(name, age, gender);
        return "자료 추가";
    }

//    @GetMapping("/updatedata")
    @PutMapping("/updatedata")
    public String upCustomer(@RequestParam(name = "name")String name,
                             @RequestParam(name = "age")int age,
                             @RequestParam(name = "gender")String gender) {
        customerService.updateCustomer(name, age, gender);
        return "자료 수정";
    }

//    @GetMapping("/deldata")
    @DeleteMapping("/deldata")
    public String delCustomer(@RequestParam(name = "name")String name) {
        customerService.deleteCustomer(name);
        return "자료 삭제";
    }
}
