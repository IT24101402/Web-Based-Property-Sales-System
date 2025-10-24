package backend.Property_Sales_System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminDashboard() {
        return "admin"; // looks for templates/admin.html
    }

    @GetMapping("/properties")
    public String propertyPage() {
        return "properties"; // templates/properties.html
    }

    @GetMapping("/users")
    public String userPage() {
        return "users"; // templates/users.html
    }

    @GetMapping("/property_form")
    public String propertyForm() {
        return "property_form"; // templates/property_form.html
    }
}

