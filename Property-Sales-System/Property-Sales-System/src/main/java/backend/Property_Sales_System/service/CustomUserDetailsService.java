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

    /**
     * Loads the user by email for Spring Security authentication.
     * We return a Spring Security UserDetails object.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + email));

        // IMPORTANT:
        // u.getRole() returns something like "tenant" | "buyer" | "vendor" | "admin"
        // Spring will treat that value as a GrantedAuthority
        // We don't force "ROLE_" prefix, because your DB doesn't store that.
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())             // principal username
                .password(u.getPassword())              // BCrypt hashed password
                .authorities(u.getRole())               // authority = "tenant", etc.
                .accountLocked(Boolean.FALSE.equals(u.getStatus())) // lock if inactive
                .build();
    }
}