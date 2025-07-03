package pack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/insertform")
    public String insertForm() {
        return "insert";
    }

    @GetMapping("/updateform")
    public String updateForm() {
        return "update";
    }

    @GetMapping("/deleteform")
    public String deleteForm() {
        return "delete";
    }
}
