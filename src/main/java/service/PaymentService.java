package backend.property_sales_system.service;

import backend.property_sales_system.model.Payment;
import backend.property_sales_system.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
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
