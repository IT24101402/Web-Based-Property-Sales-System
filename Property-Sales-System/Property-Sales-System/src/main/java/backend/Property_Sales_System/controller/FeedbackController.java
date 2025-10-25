package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.FeedbackModel;
import backend.Property_Sales_System.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

//@RestController
//@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackModel> addFeedback(@RequestBody FeedbackModel feedback, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        String email = principal.getName();
        FeedbackModel saved = feedbackService.saveFeedback(feedback, email);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackModel>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackModel> getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my")
    public ResponseEntity<List<FeedbackModel>> getMyFeedback(Principal principal) {
        String email = principal.getName();
        Long userId = feedbackService.getUserIdByEmail(email);
        if (userId == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(feedbackService.getFeedbackByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackModel> updateFeedback(@PathVariable Long id,
                                                        @RequestBody FeedbackModel updated,
                                                        Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        String email = principal.getName();
        FeedbackModel saved = feedbackService.updateFeedback(id, updated, email);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
}