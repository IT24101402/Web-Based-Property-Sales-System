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
    private static final Set<String> ALLOWED_ROLES = Set.of("buyer", "vendor", "tenant");

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "homePage/index"; // <-- subfolder
    }

    @GetMapping("/login")
    public String login() {
        return "profiles_logins_registers/login"; // <-- subfolder
    }

    @GetMapping("/register/{role}")
    public String showRegister(@PathVariable String role, Model model) {
        if (!ALLOWED_ROLES.contains(role)) return "redirect:/";
        User u = new User();
        u.setRole(role);
        u.addPhone("");
        model.addAttribute("role", role);
        model.addAttribute("user", u);
        return "profiles_logins_registers/register"; // <-- subfolder
    }

    @PostMapping("/register/{role}")
    public String register(
            @PathVariable String role,
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model
    ) {
        if (!ALLOWED_ROLES.contains(role)) return "redirect:/";
        user.setRole(role);

        if (result.hasErrors()) {
            model.addAttribute("role", role);
            if (user.getPhones() == null || user.getPhones().isEmpty()) {
                user.addPhone("");
            }
            return "profiles_logins_registers/register"; // stay here on error
        }

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

        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/buyer/dashboard")
    public String buyerDashboard() { return "dashboards/buyer_dashboard"; }

    @GetMapping("/vendor/dashboard")
    public String vendorDashboard() { return "dashboards/vendor_dashboard"; }

    @GetMapping("/tenant/dashboard")
    public String tenantDashboard() { return "dashboards/tenant_dashboard"; }
}