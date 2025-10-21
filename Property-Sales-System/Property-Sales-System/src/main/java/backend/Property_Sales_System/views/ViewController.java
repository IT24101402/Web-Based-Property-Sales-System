package backend.Property_Sales_System.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String register() {
        return "feedback-list";
    }

    @GetMapping("/create")
    public String login() {
        return "create";
    }

    @GetMapping("/edit")
    public String edit() {
        return "edit";
    }
}
