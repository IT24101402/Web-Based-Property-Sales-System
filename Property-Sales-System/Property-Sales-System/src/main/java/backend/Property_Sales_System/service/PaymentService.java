package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.Payment;
import backend.Property_Sales_System.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByTenant(String tenantId) {
        return paymentRepository.findByTenantId(tenantId);
    }

    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment paymentDetails) {
        return paymentRepository.findById(id).map(payment -> {
            payment.setTenantId(paymentDetails.getTenantId());
            payment.setDate(paymentDetails.getDate());
            payment.setAmount(paymentDetails.getAmount());
            payment.setMethod(paymentDetails.getMethod());
            payment.setStatus(paymentDetails.getStatus());
            payment.setNotes(paymentDetails.getNotes());
            return paymentRepository.save(payment);
        }).orElseThrow(() -> new RuntimeException("Payment not found with id " + id));
    }

    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id))
            throw new RuntimeException("Payment not found with id " + id);
        paymentRepository.deleteById(id);
    }
}
