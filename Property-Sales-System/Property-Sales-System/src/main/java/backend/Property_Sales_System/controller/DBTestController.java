package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Simple controller to test database connectivity and UserRepository functionality.
 */
@RestController
@RequestMapping("/api/dbtest")
@CrossOrigin(origins = "*")
public class DBTestController {

    private final UserRepository userRepository;

    @Autowired
    public DBTestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Basic endpoint to verify database connection.
     */
    @GetMapping("/check")
    public String checkDatabaseConnection() {
        try {
            long count = userRepository.count();
            return "✅ Database connection successful! Total users: " + count;
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Database connection failed: " + e.getMessage();
        }
    }
}
