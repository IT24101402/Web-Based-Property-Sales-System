package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for User-related operations.
 * Handles registration and can be extended for login/profile retrieval.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Register new user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
User newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }

    // Future endpoints: login, profile management, etc.
}
