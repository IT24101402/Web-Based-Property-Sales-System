package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.Payment;
import backend.Property_Sales_System.model.Property;
import backend.Property_Sales_System.repositories.PaymentRepository;
import backend.Property_Sales_System.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    private final PropertyService propertyService;
    private final PaymentRepository paymentRepository;

    public BuyerController(PropertyService propertyService, PaymentRepository paymentRepository) {
        this.propertyService = propertyService;
        this.paymentRepository = paymentRepository;
    }

    /** ✅ 1. Show available properties to buyer */
    @GetMapping("/properties")
    public String listAvailableProperties(Model model) {
        model.addAttribute("properties", propertyService.getAllProperties()
                .stream().filter(p -> !Boolean.TRUE.equals(p.getIsSold())).toList());
        return "buyer_properties";
    }

    /** ✅ 2. Buyer selects property to buy -> redirect to payment form */
    @GetMapping("/buy/{id}")
    public String buyProperty(@PathVariable Long id, Model model) {
        Property property = propertyService.getPropertyById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        model.addAttribute("property", property);
        model.addAttribute("payment", new Payment());
        return "payment/add-payment"; // reuse existing styled page
    }

    /** ✅ 3. Buyer submits payment */
    @PostMapping("/buy/{id}/payment")
    public String processPayment(@PathVariable Long id, @ModelAttribute Payment payment) {
        Property property = propertyService.getPropertyById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // Save payment record
        payment.setDate(LocalDate.now());
        payment.setAmount(property.getPrice());
        payment.setMethod(payment.getMethod() != null ? payment.getMethod() : "Card");
        payment.setStatus(Payment.Status.PAID);
        payment.setNotes("Property purchase for " + property.getTitle());
        paymentRepository.save(payment);

        // Mark property as sold
        property.setIsSold(true);
        property.setBuyerId(payment.getId()); // link via payment ID
        propertyService.saveProperty(property);

        return "redirect:/buyer/payment/confirm?pid=" + payment.getId();
    }

    /** ✅ 4. Confirmation page */
    @GetMapping("/payment/confirm")
    public String confirmPayment(@RequestParam("pid") Long paymentId, Model model) {
        Payment p = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        model.addAttribute("payment", p);
        return "payment/payment-confirm";
    }
}
