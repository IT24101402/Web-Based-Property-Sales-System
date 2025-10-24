package backend.Property_Sales_System.controller;

import PropertyManagment.propertyease.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@CrossOrigin("*") // allow frontend to call this
public class DBTestController {

    @Autowired
    private UserRepository userRepository;

    // API to check DB connectivity
    @GetMapping("/db")
    public ResponseEntity<String> checkDB() {
        try {
            long count = userRepository.count();
            return ResponseEntity.ok("✅ Database Connected! Users count: " + count);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("❌ Database connection failed: " + e.getMessage());
        }
    }
}
