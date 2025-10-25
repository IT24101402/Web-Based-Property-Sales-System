package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String redirectDashboard(Authentication authentication) {
        if (authentication == null) {
            // not logged in somehow
            return "redirect:/login";
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null || user.getRole() == null) {
            // fallback if something is off
            return "redirect:/";
        }

        // Decide dashboard based on role from DB
        String role = user.getRole().toLowerCase();

        return switch (role) {
            case "buyer"  -> "redirect:/buyer/dashboard";
            case "vendor" -> "redirect:/vendor/dashboard";
            case "tenant" -> "redirect:/tenant/dashboard";
            case "admin"  -> "redirect:/admin/dashboard";
            default       -> "redirect:/";
        };
    }
}