package backend.Property_Sales_System.repositories;

import backend.Property_Sales_System.model.MaintenanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for MaintenanceRequest entities.
 * Provides CRUD operations and can include custom queries.
 */
@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    // Example custom query methods:
    // List<MaintenanceRequest> findByPropertyId(Long propertyId);
    // List<MaintenanceRequest> findByStatus(String status);
}
