package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.FeedbackModel;
import backend.Property_Sales_System.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling feedback form pages and submissions.
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackViewController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackViewController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /** ✅ Show feedback form */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("feedback", new FeedbackModel());
        return "create"; // templates/create.html
    }

    /** ✅ Handle form submission */
    @PostMapping("/create")
    public String submitFeedback(@ModelAttribute("feedback") FeedbackModel feedback) {
        feedbackService.createFeedback(feedback);
        return "redirect:/feedback/list"; // redirect to feedback list
    }

    /** ✅ Show feedback list */
    @GetMapping("/list")
    public String showFeedbackList(Model model) {
        model.addAttribute("feedbackList", feedbackService.getAllFeedback());
        return "feedback-list"; // templates/feedback-list.html
    }
}
