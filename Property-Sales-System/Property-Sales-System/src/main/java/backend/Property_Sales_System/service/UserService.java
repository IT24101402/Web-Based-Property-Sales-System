package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing User entities with password encryption.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create or update a user.
     * On create: we will hash the plain text password.
     * On update: if password looks already-hashed, we won't double-hash it.
     */
    public User saveUser(User user) {

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            // Only encode if not already BCrypt (BCrypt hashes start with $2a$, $2b$, $2y$ typically)
            if (!(user.getPassword().startsWith("$2a$")
                    || user.getPassword().startsWith("$2b$")
                    || user.getPassword().startsWith("$2y$"))) {

                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        return userRepository.save(user);
    }

    /**
     * Return all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Update an existing user's profile and (optionally) password.
     */
    public void updateProfile(User current, User updatedUser) {
        if (current == null || updatedUser == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }

        // Basic profile fields
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

        // Phone numbers
        if (updatedUser.getPhones() != null && !updatedUser.getPhones().isEmpty()) {
            current.setPhones(updatedUser.getPhones());
        }

        // Password
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            String newPw = updatedUser.getPassword();
            if (!(newPw.startsWith("$2a$") || newPw.startsWith("$2b$") || newPw.startsWith("$2y$"))) {
                current.setPassword(passwordEncoder.encode(newPw));
            } else {
                current.setPassword(newPw); // already encoded
            }
        }

        userRepository.save(current);
    }
}