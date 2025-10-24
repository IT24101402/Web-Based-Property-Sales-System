package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.FeedbackModel;
import backend.Property_Sales_System.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Create new feedback
    @PostMapping
    public ResponseEntity<Map<String, Object>> createFeedback(@Valid @RequestBody FeedbackModel feedback) {
        try {
            FeedbackModel createdFeedback = feedbackService.createFeedback(feedback);
            return createSuccessResponse("Feedback created successfully", createdFeedback, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Failed to create feedback: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all feedback
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllFeedback() {
        try {
            List<FeedbackModel> feedbackList = feedbackService.getAllFeedback();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Feedback retrieved successfully");
            response.put("data", feedbackList);
            response.put("count", feedbackList.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve feedback: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getFeedbackById(@PathVariable Long id) {
        try {
            Optional<FeedbackModel> feedback = feedbackService.getFeedbackById(id);
            if (feedback.isPresent()) {
                return createSuccessResponse("Feedback retrieved successfully", feedback.get(), HttpStatus.OK);
            } else {
                return createErrorResponse("Feedback not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid ID: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve feedback: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update feedback
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateFeedback(@PathVariable Long id, @Valid @RequestBody FeedbackModel feedback) {
        try {
            FeedbackModel updatedFeedback = feedbackService.updateFeedback(id, feedback);
            return createSuccessResponse("Feedback updated successfully", updatedFeedback, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return createErrorResponse("Failed to update feedback: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return createErrorResponse("Failed to update feedback: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete feedback
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteFeedback(@PathVariable Long id) {
        try {
            boolean deleted = feedbackService.deleteFeedback(id);
            if (deleted) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Feedback deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                return createErrorResponse("Failed to delete feedback", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid ID: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return createErrorResponse("Failed to delete feedback: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return createErrorResponse("Failed to delete feedback: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get feedback by role
    @GetMapping("/role/{role}")
    public ResponseEntity<Map<String, Object>> getFeedbackByRole(@PathVariable String role) {
        try {
            List<FeedbackModel> feedbackList = feedbackService.getFeedbackByRole(role);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Feedback retrieved successfully for role: " + role);
            response.put("data", feedbackList);
            response.put("count", feedbackList.size());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid role: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve feedback by role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get feedback by type
    @GetMapping("/type/{type}")
    public ResponseEntity<Map<String, Object>> getFeedbackByType(@PathVariable String type) {
        try {
            List<FeedbackModel> feedbackList = feedbackService.getFeedbackByType(type);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Feedback retrieved successfully for type: " + type);
            response.put("data", feedbackList);
            response.put("count", feedbackList.size());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid type: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve feedback by type: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get feedback by rating
    @GetMapping("/rating/{rating}")
    public ResponseEntity<Map<String, Object>> getFeedbackByRating(@PathVariable Integer rating) {
        try {
            List<FeedbackModel> feedbackList = feedbackService.getFeedbackByRating(rating);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Feedback retrieved successfully for rating: " + rating);
            response.put("data", feedbackList);
            response.put("count", feedbackList.size());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid rating: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve feedback by rating: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get feedback by date range
    @GetMapping("/date-range")
    public ResponseEntity<Map<String, Object>> getFeedbackByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            
            List<FeedbackModel> feedbackList = feedbackService.getFeedbackByDateRange(start, end);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Feedback retrieved successfully for date range: " + startDate + " to " + endDate);
            response.put("data", feedbackList);
            response.put("count", feedbackList.size());
            return ResponseEntity.ok(response);
        } catch (DateTimeParseException e) {
            return createErrorResponse("Invalid date format. Use YYYY-MM-DD format", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid date range: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve feedback by date range: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search feedback by subject
    @GetMapping("/search/subject")
    public ResponseEntity<Map<String, Object>> searchFeedbackBySubject(@RequestParam String keyword) {
        try {
            List<FeedbackModel> feedbackList = feedbackService.searchFeedbackBySubject(keyword);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Feedback search completed for subject keyword: " + keyword);
            response.put("data", feedbackList);
            response.put("count", feedbackList.size());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid search keyword: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Failed to search feedback by subject: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search feedback by description
    @GetMapping("/search/description")
    public ResponseEntity<Map<String, Object>> searchFeedbackByDescription(@RequestParam String keyword) {
        try {
            List<FeedbackModel> feedbackList = feedbackService.searchFeedbackByDescription(keyword);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Feedback search completed for description keyword: " + keyword);
            response.put("data", feedbackList);
            response.put("count", feedbackList.size());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid search keyword: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Failed to search feedback by description: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get feedback ordered by date (newest first)
    @GetMapping("/ordered/date")
    public ResponseEntity<Map<String, Object>> getFeedbackOrderedByDate() {
        try {
            List<FeedbackModel> feedbackList = feedbackService.getFeedbackOrderedByDate();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Feedback retrieved successfully, ordered by date (newest first)");
            response.put("data", feedbackList);
            response.put("count", feedbackList.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve feedback ordered by date: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get feedback ordered by rating (highest first)
    @GetMapping("/ordered/rating")
    public ResponseEntity<Map<String, Object>> getFeedbackOrderedByRating() {
        try {
            List<FeedbackModel> feedbackList = feedbackService.getFeedbackOrderedByRating();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Feedback retrieved successfully, ordered by rating (highest first)");
            response.put("data", feedbackList);
            response.put("count", feedbackList.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve feedback ordered by rating: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get high rating feedback (4 and above)
    @GetMapping("/high-rating")
    public ResponseEntity<Map<String, Object>> getHighRatingFeedback() {
        try {
            List<FeedbackModel> feedbackList = feedbackService.getHighRatingFeedback();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "High rating feedback retrieved successfully (rating 4 and above)");
            response.put("data", feedbackList);
            response.put("count", feedbackList.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve high rating feedback: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to create success response
    private ResponseEntity<Map<String, Object>> createSuccessResponse(String message, Object data, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.status(status).body(response);
    }

    // Helper method to create error response
    private ResponseEntity<Map<String, Object>> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", message);
        response.put("timestamp", java.time.LocalDateTime.now());
        return ResponseEntity.status(status).body(response);
    }
}