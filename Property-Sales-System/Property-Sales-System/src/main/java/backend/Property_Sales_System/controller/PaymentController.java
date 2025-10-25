package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.Payment;
import backend.Property_Sales_System.model.Property;
import backend.Property_Sales_System.model.User;
import backend.Property_Sales_System.repositories.PaymentRepository;
import backend.Property_Sales_System.repositories.PropertyRepository;
import backend.Property_Sales_System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired private PaymentRepository paymentRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private UserService userService;

    /** Buyer: show prefilled payment page for selected property */
    @GetMapping("/buyer/property/{propertyId}/buy")
    public String showPaymentPage(@PathVariable Long propertyId, Authentication auth, Model model) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Payment payment = new Payment();
        payment.setProperty(property);
        payment.setDate(LocalDate.now());
        payment.setAmount(property.getPrice());
        payment.setMethod("Card");
        payment.setStatus(Payment.Status.PAID);

        model.addAttribute("property", property);
        model.addAttribute("payment", payment);
        model.addAttribute("buyerEmail", auth != null ? auth.getName() : "guest@example.com");
        return "payment/add-payment";
    }

    /** Buyer: handle payment confirmation */
    @PostMapping("/buyer/property/pay")
    public String handlePayment(@ModelAttribute Payment payment,
                                @RequestParam Long propertyId,
                                Authentication auth,
                                RedirectAttributes redirectAttrs) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        String email = auth != null ? auth.getName() : "guest@example.com";
        User buyer = userService.getAllUsers().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);

        payment.setProperty(property);
        payment.setAmount(property.getPrice());
        payment.setDate(LocalDate.now());
        payment.setStatus(Payment.Status.PAID);
        payment.setBuyerEmail(email);
        if (buyer != null) payment.setBuyerId(buyer.getId());

        paymentRepository.save(payment);

        property.setSold(true);
        property.setBuyerId(payment.getBuyerId());
        propertyRepository.save(property);

        redirectAttrs.addFlashAttribute("payment", payment);
        redirectAttrs.addFlashAttribute("property", property);
        return "redirect:/payment/confirmation";
    }

    /** Buyer: payment confirmation */
    @GetMapping("/payment/confirmation")
    public String confirmation() {
        return "payment/payment-confirmation";
    }

    /** Buyer: view all their payments */
    @GetMapping("/buyer/payments")
    public String buyerPayments(Authentication auth, Model model) {
        String email = auth != null ? auth.getName() : "guest@example.com";
        User buyer = userService.getAllUsers().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);

        if (buyer == null) {
            model.addAttribute("error", "No buyer found for your session.");
            return "payment/buyer-payments";
        }

        List<Payment> payments = paymentRepository.findByBuyerId(buyer.getId());
        model.addAttribute("payments", payments);
        return "payment/buyer-payments";
    }

    /** Admin: view all payments */
    @GetMapping("/admin/payment-records")
    public String adminPayments(Model model) {
        model.addAttribute("payments", paymentRepository.findAll());
        return "admin/admin-payments";
    }

    // /** Admin: edit payment form */
    // @GetMapping("/admin/payments/edit/{id}")
    // public String editPaymentForm(@PathVariable Long id, Model model) {
    //     Payment payment = paymentRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Payment not found"));
    //     model.addAttribute("payment", payment);
    //     model.addAttribute("statuses", Payment.Status.values());
    //     return "admin/edit-payment";
    // }

    // /** Admin: save edited payment */
    // @PostMapping("/admin/payments/edit/{id}")
    // public String updatePayment(@PathVariable Long id, @ModelAttribute Payment updated) {
    //     Payment payment = paymentRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Payment not found"));
    //     payment.setAmount(updated.getAmount());
    //     payment.setDate(updated.getDate());
    //     payment.setMethod(updated.getMethod());
    //     payment.setStatus(updated.getStatus());
    //     payment.setNotes(updated.getNotes());
    //     paymentRepository.save(payment);
    //     return "redirect:/admin/payments";
    // }

    // /** Admin: delete with confirmation */
    // @GetMapping("/admin/payments/delete/{id}")
    // public String deletePayment(@PathVariable Long id) {
    //     paymentRepository.deleteById(id);
    //     return "redirect:/admin/payments";
    // }
}
