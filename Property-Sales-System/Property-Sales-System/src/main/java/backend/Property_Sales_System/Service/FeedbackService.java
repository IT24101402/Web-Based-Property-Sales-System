package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.FeedbackModel;
import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repositories.FeedbackRepository;
import backend.Property_Sales_System.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    // ✅ Save feedback and attach logged-in user
    public FeedbackModel saveFeedback(FeedbackModel feedback, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        feedback.setUser(user);               // attach user entity
        feedback.setRole(user.getRole());     // copy for display
        feedback.setDate(LocalDate.now());    // set current date

        return feedbackRepository.save(feedback);
    }

    // ✅ Get all feedback (ensures user info is loaded for JSON)
    public List<FeedbackModel> getAllFeedback() {
        List<FeedbackModel> feedbacks = feedbackRepository.findAll();

        feedbacks.forEach(f -> {
            if (f.getUser() != null) {
                f.getUser().getId();   // force-load user ID
                f.getUser().getRole(); // ensure role is available
            }
        });

        return feedbacks;
    }

    // ✅ Get feedback by user ID
    public List<FeedbackModel> getFeedbackByUser(Long userId) {
        List<FeedbackModel> feedbacks = feedbackRepository.findByUser_Id(userId);

        feedbacks.forEach(f -> {
            if (f.getUser() != null) {
                f.getUser().getId();
                f.getUser().getRole();
            }
        });

        return feedbacks;
    }

    // ✅ Get single feedback
    public Optional<FeedbackModel> getFeedbackById(Long id) {
        Optional<FeedbackModel> feedbackOpt = feedbackRepository.findById(id);

        feedbackOpt.ifPresent(f -> {
            if (f.getUser() != null) {
                f.getUser().getId();
                f.getUser().getRole();
            }
        });

        return feedbackOpt;
    }

    // ✅ Update feedback (keep user + role consistent)
    public FeedbackModel updateFeedback(Long id, FeedbackModel updated, String email) {
        FeedbackModel existing = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with ID: " + id));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // update editable fields
        existing.setType(updated.getType());
        existing.setSubject(updated.getSubject());
        existing.setDescription(updated.getDescription());
        existing.setRating(updated.getRating());
        existing.setConsentGiven(updated.getConsentGiven());

        // refresh system-managed fields
        existing.setUser(user);               // relink user
        existing.setRole(user.getRole());     // sync role
        existing.setDate(LocalDate.now());    // update date to now

        return feedbackRepository.save(existing);
    }

    // ✅ Get user ID by email
    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElse(null);
    }

    // ✅ Delete feedback
    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
}