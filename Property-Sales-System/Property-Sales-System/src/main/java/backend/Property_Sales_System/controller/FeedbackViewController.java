package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.FeedbackModel;
import backend.Property_Sales_System.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/feedback")
public class FeedbackViewController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackViewController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Show feedback form (only for logged-in users)
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("feedback", new FeedbackModel());
        return "create";
    }

    // Handle form submission
    @PostMapping("/create")
    public String submitFeedback(@ModelAttribute("feedback") FeedbackModel feedback, Principal principal) {
        if (principal == null) {
            // if somehow accessed without login
            return "redirect:/login";
        }

        String userEmail = principal.getName();
        feedbackService.saveFeedback(feedback, userEmail);
        return "redirect:/feedback/list";
    }

    // Show feedback list
    @GetMapping("/list")
    public String showFeedbackList(Model model) {
        model.addAttribute("feedbackList", feedbackService.getAllFeedback());
        return "feedback-list";
    }
}