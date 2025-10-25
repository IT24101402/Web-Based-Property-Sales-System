package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.model.UserPhone;
import backend.Property_Sales_System.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Set;

@Controller
public class AuthController {

    private final UserService userService;
    private static final Set<String> ALLOWED_ROLES = Set.of("buyer", "vendor", "tenant", "admin");

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Home page
    @GetMapping("/")
    public String home() {
        return "homePage/index";
    }

    // Login page
    @GetMapping("/login")
    public String login() {
        return "profiles_logins_registers/login";
    }

    // Show register form for buyer/vendor/tenant/admin
    @GetMapping("/register/{role}")
    public String showRegister(@PathVariable String role, Model model) {
        if (!ALLOWED_ROLES.contains(role)) return "redirect:/";

        User u = new User();
        u.setRole(role);
        u.addPhone(""); // one empty phone field for the form

        model.addAttribute("role", role);
        model.addAttribute("user", u);

        return "profiles_logins_registers/register";
    }

    // Handle registration submit
    @PostMapping("/register/{role}")
    public String register(
            @PathVariable String role,
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model
    ) {
        if (!ALLOWED_ROLES.contains(role)) return "redirect:/";

        user.setRole(role);

        // If validation errors: re-render form
        if (result.hasErrors()) {
            model.addAttribute("role", role);

            if (user.getPhones() == null || user.getPhones().isEmpty()) {
                user.addPhone("");
            }
            return "profiles_logins_registers/register";
        }

        // Clean/attach phones
        if (user.getPhones() != null) {
            Iterator<UserPhone> it = user.getPhones().iterator();
            while (it.hasNext()) {
                UserPhone p = it.next();
                if (p.getPhoneNumber() == null || p.getPhoneNumber().isBlank()) {
                    it.remove();
                } else {
                    p.setUser(user);
                }
            }
        }

        // Save user (password will be encoded in UserService)
        userService.saveUser(user);

        // After register, go to login
        return "redirect:/login";
    }

    // Dashboards:
    @GetMapping("/buyer/dashboard")
    public String buyerDashboard() {
        return "dashboards/buyer_dashboard";
    }

    // @GetMapping("/vendor/dashboard")
    // public String vendorDashboardRedirect() {
    //     return "redirect:/vendor/dashboard"; // handled by VendorController now
    // }
    
    @GetMapping("/tenant/dashboard")
    public String tenantDashboard() {
        return "dashboards/tenant_dashboard";
    }
    // @GetMapping("/admin/dashboard")
    // public String adminDashboard() {
    //     return "admin/dashboard";   // loads templates/admin/dashboard.html
    // }
    
    // // OPTIONAL: in case you add admin later
    // @GetMapping("/admin/dashboard")
    // public String adminDashboard() {
    //     return "dashboards/admin_dashboard"; // make sure you have this template if admins exist
    // }
}