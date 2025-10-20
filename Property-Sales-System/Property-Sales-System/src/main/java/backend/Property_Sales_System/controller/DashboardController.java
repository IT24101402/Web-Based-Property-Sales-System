package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// DashboardController.java
@Controller
public class DashboardController {

    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String redirectDashboard(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        return switch (user.getRole()) {
            case "buyer"  -> "redirect:/buyer/dashboard";
            case "vendor" -> "redirect:/vendor/dashboard";
            case "tenant" -> "redirect:/tenant/dashboard";
            case "admin"  -> "redirect:/admin/dashboard";
            default       -> "redirect:/";
        };
    }
}