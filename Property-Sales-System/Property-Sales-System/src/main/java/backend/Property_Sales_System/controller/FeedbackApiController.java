package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.FeedbackModel;
import backend.Property_Sales_System.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackApiController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackApiController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Get all feedback
    @GetMapping
    public ResponseEntity<List<FeedbackModel>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    // Get feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFeedbackById(@PathVariable Long id) {
        Optional<FeedbackModel> feedbackOpt = feedbackService.getFeedbackById(id);
        if (feedbackOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Feedback not found");
        }
        return ResponseEntity.ok(feedbackOpt.get());
    }

    // Create new feedback
    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody FeedbackModel feedback, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        String email = principal.getName();
        FeedbackModel saved = feedbackService.saveFeedback(feedback, email);
        return ResponseEntity.ok(saved);
    }

    // Update feedback
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFeedback(@PathVariable Long id, @RequestBody FeedbackModel updated, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        String email = principal.getName();
        FeedbackModel saved = feedbackService.updateFeedback(id, updated, email);
        return ResponseEntity.ok(saved);
    }

    // Delete feedback
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok("Feedback deleted");
    }
}