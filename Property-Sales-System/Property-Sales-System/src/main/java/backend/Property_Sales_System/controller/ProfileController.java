package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repositories.UserRepository;
import backend.Property_Sales_System.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final UserService userService;

    public ProfileController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public String showProfile(Model model, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        model.addAttribute("user", user);
        return "profiles_logins_registers/profile"; // adjust to your folder
    }

    @PostMapping
    public String updateProfile(@ModelAttribute User updatedUser, Authentication authentication) {
        User current = userRepository.findByEmail(authentication.getName()).orElseThrow();
        userService.updateProfile(current, updatedUser);  // <-- encodes if new password provided
        return "redirect:/dashboard";
    }
}