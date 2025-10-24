package backend.Property_Sales_System.Repository;

import backend.Property_Sales_System.model.Lease;
import backend.Property_Sales_System.model.LeaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByStatus(LeaseStatus status);
}
