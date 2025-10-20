package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    /** Registration */
    @Transactional
    public User saveUser(User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        user.setPassword(encoder.encode(user.getPassword())); // encode on sign-up
        return repo.save(user);
    }

    /** Profile update (encode new password if provided) */
    @Transactional
    public User updateProfile(User persistentUser, User formUser) {
        // copy editable fields
        persistentUser.setUsername(formUser.getUsername());
        persistentUser.setEmail(formUser.getEmail());

        // only encode & set if user typed something
        if (StringUtils.hasText(formUser.getPassword())) {
            persistentUser.setPassword(encoder.encode(formUser.getPassword()));
        }
        return repo.save(persistentUser);
    }
}