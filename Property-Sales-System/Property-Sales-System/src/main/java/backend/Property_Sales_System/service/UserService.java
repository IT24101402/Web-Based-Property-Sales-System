package backend.Property_Sales_System.Service;

import PropertyManagment.propertyease.backend.model.User;
import PropertyManagment.propertyease.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user); // Used for registration
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}