package backend.Property_Sales_System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Secondary admin controller for property/user management pages
 * (avoid duplicating /admin route handled by PageController).
 */
@Controller
public class AdminController {

    // ✅ Remove the duplicate /admin mapping
    // This route is already handled by PageController

    @GetMapping("/admin/properties/manage")
    public String propertyManagement() {
        return "properties"; // templates/properties.html
    }

    @GetMapping("/admin/users/manage")
    public String userManagement() {
        return "users"; // templates/users.html
    }

    @GetMapping("/admin/properties/form")
    public String propertyForm() {
        return "property_form"; // templates/property_form.html
    }
}
