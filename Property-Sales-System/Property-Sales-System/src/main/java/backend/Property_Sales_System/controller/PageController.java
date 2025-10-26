package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.service.PropertyService;
import backend.Property_Sales_System.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for rendering admin pages and binding data to templates.
 */
@Controller
public class PageController {

    @SuppressWarnings("unused")
    private final PropertyService propertyService;
    @SuppressWarnings("unused")
    private final PaymentRepository paymentRepository;
    
    @Autowired
    public PageController(PropertyService propertyService, PaymentRepository paymentRepository) {
        this.propertyService = propertyService;
        this.paymentRepository = paymentRepository;
    }

    /**  Admin dashboard page */
    @GetMapping("/admin")
    public String adminDashboard() {
        return "admin"; // templates/admin.html
    }

    // /**  Property management page */
    // @GetMapping("/admin/properties")
    // public String propertyPage(Model model) {
    //     model.addAttribute("properties", propertyService.getAllProperties());
    //     return "property_page"; // templates/property_page.html
    // }

    // /**  User management page */
    // @GetMapping("/admin/users")
    // public String userManagementPage() {
    //     return "user_management"; // templates/user_management.html
    // }

    /**  System settings page */
    @GetMapping("/admin/settings")
    public String systemSettingsPage() {
        return "system_settings"; // templates/system_settings.html
    }

    // /**  New: Payment listing page */
    // @GetMapping("/admin/payments")
    // public String paymentsPage(Model model) {
    //     List<Payment> payments = paymentRepository.findAll();
    //     model.addAttribute("payments", payments);
    //     return "admin/admin-payments"; // templates/admin/admin-payments.html
    // }
}
