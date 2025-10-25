package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.Property;
import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repositories.PropertyRepository;
import backend.Property_Sales_System.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AdminService
 * Handles admin-level operations such as managing users and properties.
 */
@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    // --- User Management ---
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found for this id: " + id));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setStreet(userDetails.getStreet());
        user.setCity(userDetails.getCity());
        user.setDistrict(userDetails.getDistrict());
        user.setStatus(userDetails.getStatus());

        return userRepository.save(user);
    }

    // --- Property Management ---
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
