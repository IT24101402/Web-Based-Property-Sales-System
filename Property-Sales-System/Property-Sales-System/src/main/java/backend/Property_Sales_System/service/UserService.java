package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing User entities.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save or update a user.
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Get all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Update a user’s basic profile (no password encoding).
     */
    public void updateProfile(User current, User updatedUser) {
        if (current == null || updatedUser == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }

        // ✅ Update basic profile fields
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().isBlank()) {
            current.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
            current.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getStreet() != null && !updatedUser.getStreet().isBlank()) {
            current.setStreet(updatedUser.getStreet());
        }
        if (updatedUser.getCity() != null && !updatedUser.getCity().isBlank()) {
            current.setCity(updatedUser.getCity());
        }
        if (updatedUser.getDistrict() != null && !updatedUser.getDistrict().isBlank()) {
            current.setDistrict(updatedUser.getDistrict());
        }

        // ✅ Update phone numbers
        if (updatedUser.getPhones() != null && !updatedUser.getPhones().isEmpty()) {
            current.setPhones(updatedUser.getPhones());
        }

        // ✅ Save updated record
        userRepository.save(current);
    }
}
