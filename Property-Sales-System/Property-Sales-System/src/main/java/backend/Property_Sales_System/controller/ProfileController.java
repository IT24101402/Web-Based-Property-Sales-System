package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repositories.UserRepository;
import backend.Property_Sales_System.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user profile display and updates.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final UserService userService;

    public ProfileController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /** ✅ Display the logged-in user's profile */
    @GetMapping
    public String showProfile(Model model, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "profiles_logins_registers/profile";
    }

    /** ✅ Handle profile update submissions */
    @PostMapping
    public String updateProfile(@ModelAttribute User updatedUser, Authentication authentication) {
        User current = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userService.updateProfile(current, updatedUser);
        return "redirect:/dashboard";
    }
}
