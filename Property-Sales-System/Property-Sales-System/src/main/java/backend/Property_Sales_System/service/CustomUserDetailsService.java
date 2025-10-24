package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + email));

        // Convert your User entity into a Spring Security User
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())     // login with email
                .password(u.getPassword())      // password already encoded with BCrypt
                .authorities(u.getRole())       // "buyer", "vendor", "tenant"
                .accountLocked(Boolean.FALSE.equals(u.getStatus())) // lock if inactive
                .build();
    }
}
