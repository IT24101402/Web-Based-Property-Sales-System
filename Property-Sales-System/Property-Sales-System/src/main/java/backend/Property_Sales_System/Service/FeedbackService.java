package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.FeedbackModel;
import backend.Property_Sales_System.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // Create new feedback
    public FeedbackModel createFeedback(FeedbackModel feedback) {
        try {
            // Set submission date to current date if not provided
            if (feedback.getDate() == null) {
                feedback.setDate(LocalDate.now());
            }
            
            // Validate required fields
            validateFeedback(feedback);
            
            return feedbackRepository.save(feedback);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create feedback: " + e.getMessage(), e);
        }
    }

    // Get all feedback
    public List<FeedbackModel> getAllFeedback() {
        try {
            return feedbackRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all feedback: " + e.getMessage(), e);
        }
    }

    // Get feedback by ID
    public Optional<FeedbackModel> getFeedbackById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid feedback ID");
            }
            return feedbackRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve feedback by ID: " + e.getMessage(), e);
        }
    }

    // Update feedback
    public FeedbackModel updateFeedback(Long id, FeedbackModel updatedFeedback) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid feedback ID");
            }

            Optional<FeedbackModel> existingFeedback = feedbackRepository.findById(id);
            if (existingFeedback.isEmpty()) {
                throw new RuntimeException("Feedback not found with ID: " + id);
            }

            FeedbackModel feedback = existingFeedback.get();
            
            // Update fields
            if (updatedFeedback.getRole() != null) {
                feedback.setRole(updatedFeedback.getRole());
            }
            if (updatedFeedback.getType() != null) {
                feedback.setType(updatedFeedback.getType());
            }
            if (updatedFeedback.getSubject() != null) {
                feedback.setSubject(updatedFeedback.getSubject());
            }
            if (updatedFeedback.getDescription() != null) {
                feedback.setDescription(updatedFeedback.getDescription());
            }
            if (updatedFeedback.getRating() != null) {
                feedback.setRating(updatedFeedback.getRating());
            }
            if (updatedFeedback.getConsentGiven() != null) {
                feedback.setConsentGiven(updatedFeedback.getConsentGiven());
            }

            validateFeedback(feedback);
            return feedbackRepository.save(feedback);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update feedback: " + e.getMessage(), e);
        }
    }

    // Delete feedback
    public boolean deleteFeedback(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid feedback ID");
            }

            if (!feedbackRepository.existsById(id)) {
                throw new RuntimeException("Feedback not found with ID: " + id);
            }

            feedbackRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete feedback: " + e.getMessage(), e);
        }
    }

    // Get feedback by role
    public List<FeedbackModel> getFeedbackByRole(String role) {
        try {
            if (role == null || role.trim().isEmpty()) {
                throw new IllegalArgumentException("Role cannot be null or empty");
            }
            return feedbackRepository.findByRole(role);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve feedback by role: " + e.getMessage(), e);
        }
    }

    // Get feedback by type
    public List<FeedbackModel> getFeedbackByType(String type) {
        try {
            if (type == null || type.trim().isEmpty()) {
                throw new IllegalArgumentException("Type cannot be null or empty");
            }
            return feedbackRepository.findByType(type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve feedback by type: " + e.getMessage(), e);
        }
    }

    // Get feedback by rating
    public List<FeedbackModel> getFeedbackByRating(Integer rating) {
        try {
            if (rating == null || rating < 1 || rating > 5) {
                throw new IllegalArgumentException("Rating must be between 1 and 5");
            }
            return feedbackRepository.findByRating(rating);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve feedback by rating: " + e.getMessage(), e);
        }
    }

    // Get feedback by date range
    public List<FeedbackModel> getFeedbackByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("Start date and end date cannot be null");
            }
            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
            return feedbackRepository.findByDateBetween(startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve feedback by date range: " + e.getMessage(), e);
        }
    }

    // Search feedback by subject keyword
    public List<FeedbackModel> searchFeedbackBySubject(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                throw new IllegalArgumentException("Search keyword cannot be null or empty");
            }
            return feedbackRepository.findBySubjectContaining(keyword.trim());
        } catch (Exception e) {
            throw new RuntimeException("Failed to search feedback by subject: " + e.getMessage(), e);
        }
    }

    // Search feedback by description keyword
    public List<FeedbackModel> searchFeedbackByDescription(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                throw new IllegalArgumentException("Search keyword cannot be null or empty");
            }
            return feedbackRepository.findByDescriptionContaining(keyword.trim());
        } catch (Exception e) {
            throw new RuntimeException("Failed to search feedback by description: " + e.getMessage(), e);
        }
    }

    // Get feedback ordered by date (newest first)
    public List<FeedbackModel> getFeedbackOrderedByDate() {
        try {
            return feedbackRepository.findAllByOrderByDateDesc();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve feedback ordered by date: " + e.getMessage(), e);
        }
    }

    // Get feedback ordered by rating (highest first)
    public List<FeedbackModel> getFeedbackOrderedByRating() {
        try {
            return feedbackRepository.findAllByOrderByRatingDesc();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve feedback ordered by rating: " + e.getMessage(), e);
        }
    }

    // Get feedback with high ratings (4 and above)
    public List<FeedbackModel> getHighRatingFeedback() {
        try {
            return feedbackRepository.findByRatingGreaterThanEqual(4);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve high rating feedback: " + e.getMessage(), e);
        }
    }

    // Private method to validate feedback
    private void validateFeedback(FeedbackModel feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback cannot be null");
        }
        
        if (feedback.getRole() == null || feedback.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Role is required");
        }
        
        if (feedback.getType() == null || feedback.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Feedback type is required");
        }
        
        if (feedback.getSubject() == null || feedback.getSubject().trim().isEmpty()) {
            throw new IllegalArgumentException("Subject is required");
        }
        
        if (feedback.getDescription() == null || feedback.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description is required");
        }
        
        if (feedback.getRating() == null || feedback.getRating() < 1 || feedback.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        if (feedback.getConsentGiven() == null || !feedback.getConsentGiven()) {
            throw new IllegalArgumentException("Consent must be given");
        }
    }
}