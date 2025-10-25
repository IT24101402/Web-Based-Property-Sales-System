package backend.Property_Sales_System.repositories;

import backend.Property_Sales_System.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByTenantId(String tenantId);
    List<Payment> findByBuyerId(Long buyerId);
}
