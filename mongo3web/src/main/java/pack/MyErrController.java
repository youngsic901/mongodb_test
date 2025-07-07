package pack;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyErrController implements ErrorController {
    @GetMapping("/error")
    public String error(HttpServletRequest request) {
        return "redirect:/login";
    }
}
