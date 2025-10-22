package backend.property_sales_system.controller;

import backend.property_sales_system.model.Payment;
import backend.property_sales_system.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    // Show Add Payment Form
    @GetMapping("/add-payment")
    public String showAddPaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("methods", new String[]{"Cash","Card","Cheque","Bank Deposit","Other"});
        model.addAttribute("statuses", Payment.Status.values());
        return "add-payment";
    }

    // Save Payment
    @PostMapping("/add-payment")
    public String savePayment(@ModelAttribute Payment payment) {
        paymentRepository.save(payment);
        return "redirect:/payments";
    }

    // View all payments
    @GetMapping("/payments")
    public String viewPayments(Model model) {
        model.addAttribute("payments", paymentRepository.findAll());
        return "payments";
    }

    // Show Update Payment Form
    @GetMapping("/payments/update/{id}")
    public String showUpdatePaymentForm(@PathVariable Long id, Model model) {
        Optional<Payment> paymentOpt = paymentRepository.findById(id);
        if(paymentOpt.isPresent()) {
            model.addAttribute("payment", paymentOpt.get());
            model.addAttribute("methods", new String[]{"Cash","Card","Cheque","Bank Deposit","Other"});
            model.addAttribute("statuses", Payment.Status.values());
            return "update-payment";
        }
        return "redirect:/payments";
    }

    // Update Payment
    @PostMapping("/payments/update/{id}")
    public String updatePayment(@PathVariable Long id, @ModelAttribute Payment payment) {
        payment.setId(id);
        paymentRepository.save(payment);
        return "redirect:/payments";
    }

    // Delete Payment
    @GetMapping("/payments/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentRepository.deleteById(id);
        return "redirect:/payments";
    }
}
