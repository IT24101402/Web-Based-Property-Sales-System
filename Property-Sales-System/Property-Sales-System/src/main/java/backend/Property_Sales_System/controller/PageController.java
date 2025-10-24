package backend.Property_Sales_System.controller;

import PropertyManagment.propertyease.backend.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // CRITICAL: Tells Spring to map URLs to methods in this class
public class PageController {

    private final PropertyService propertyService;

    // Assuming you have the PropertyService set up for fetching data
    @Autowired
    public PageController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // 1. Handles the Dashboard Link (th:href="@{/admin}")
    @GetMapping("/admin") // CRITICAL: Maps the root /admin URL
    public String adminDashboard() {
        return "admin"; // Returns the template name: src/main/resources/templates/admin.html
    }

    // 2. Handles the Property Management Link (th:href="@{/admin/properties}")
    @GetMapping("/admin/properties") // CRITICAL: Maps the /admin/properties URL
    public String propertyPage(Model model) {
        // This is where the function logic goes: Fetch data
        model.addAttribute("properties", propertyService.getAllProperties());
        return "property_page"; // Returns the template name: src/main/resources/templates/property_page.html
    }

    // 3. Placeholder for User Management Link (th:href="@{/admin/users}")
    @GetMapping("/admin/users")
    public String userManagementPage() {
        return "user_management";
    }

    // 4. Placeholder for Settings Link (th:href="@{/admin/settings}")
    @GetMapping("/admin/settings")
    public String systemSettingsPage() {
        return "system_settings";
    }
}
