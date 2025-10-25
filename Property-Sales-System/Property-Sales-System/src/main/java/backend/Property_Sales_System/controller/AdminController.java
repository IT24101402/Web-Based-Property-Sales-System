package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.*;
import backend.Property_Sales_System.repositories.*;
import backend.Property_Sales_System.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserService userService;
    @Autowired private FeedbackRepository feedbackRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private PaymentService paymentService;

    /** 🏠 Dashboard */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("userCount", userService.getAllUsers().size());
        model.addAttribute("feedbackCount", feedbackRepository.findAll().size());
        model.addAttribute("propertyCount", propertyRepository.findAll().size());
        model.addAttribute("paymentCount", paymentRepository.findAll().size());
        return "admin/dashboard";
    }

    /** 👤 Users list */
    @GetMapping("/users")
    public String listUsers(@RequestParam(required = false) String role,
                            @RequestParam(required = false) String search,
                            Model model) {

        List<User> users = userService.getAllUsers();

        if (role != null && !role.isBlank() && !role.equals("all")) {
            users = users.stream()
                    .filter(u -> u.getRole().equalsIgnoreCase(role))
                    .collect(Collectors.toList());
        }
        if (search != null && !search.isBlank()) {
            users = users.stream()
                    .filter(u -> u.getEmail().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("users", users);
        model.addAttribute("selectedRole", role);
        model.addAttribute("search", search);
        return "admin/user-list";
    }

    /** ✏️ Edit user (GET form) */
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getAllUsers()
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    /** 💾 Update user (POST submit) */
    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser) {
        User current = userService.getAllUsers()
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        userService.updateProfile(current, updatedUser);
        return "redirect:/admin/users";
    }

    /** ❌ Delete user */
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    /** 💬 Feedbacks list */
    @GetMapping("/feedbacks")
    public String feedbacks(@RequestParam(required = false) String role,
                            @RequestParam(required = false) Integer rating,
                            Model model) {

        List<FeedbackModel> feedbacks = feedbackRepository.findAllByOrderByDateDesc();

        if (role != null && !role.isBlank() && !role.equals("all")) {
            feedbacks = feedbacks.stream()
                    .filter(f -> f.getRole().equalsIgnoreCase(role))
                    .collect(Collectors.toList());
        }
        if (rating != null && rating > 0) {
            feedbacks = feedbacks.stream()
                    .filter(f -> f.getRating() >= rating)
                    .collect(Collectors.toList());
        }

        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("selectedRole", role);
        model.addAttribute("selectedRating", rating);
        return "admin/feedback-list";
    }

    /** 🏘 Properties list */
    @GetMapping("/properties")
    public String properties(@RequestParam(required = false) String city,
                             @RequestParam(required = false) String district,
                             Model model) {

        List<Property> props = propertyRepository.findAll();

        if (city != null && !city.isBlank()) {
            props = props.stream()
                    .filter(p -> p.getCity() != null && p.getCity().toLowerCase().contains(city.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (district != null && !district.isBlank()) {
            props = props.stream()
                    .filter(p -> p.getDistrict() != null && p.getDistrict().toLowerCase().contains(district.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("properties", props);
        model.addAttribute("city", city);
        model.addAttribute("district", district);
        return "admin/property_page";
    }

    /** 💳 Payments list + edit/delete (already implemented) */
    @GetMapping("/payments")
    public String payments(@RequestParam(required = false) String status, Model model) {
        List<Payment> payments = paymentRepository.findAll();
        if (status != null && !status.isBlank() && !status.equals("all")) {
            payments = payments.stream()
                    .filter(p -> p.getStatus() != null && p.getStatus().name().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }
        model.addAttribute("payments", payments);
        model.addAttribute("selectedStatus", status);
        return "admin/payment-records";
    }

    @GetMapping("/payments/edit/{id}")
    public String editPayment(@PathVariable Long id, Model model) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        model.addAttribute("payment", payment);
        model.addAttribute("statuses", Payment.Status.values());
        return "admin/edit-payment";
    }

    @PostMapping("/payments/edit/{id}")
    public String updatePayment(@PathVariable Long id, @ModelAttribute Payment payment) {
        paymentService.updatePayment(id, payment);
        return "redirect:/admin/payments";
    }

    @GetMapping("/payments/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "redirect:/admin/payments";
    }
}
