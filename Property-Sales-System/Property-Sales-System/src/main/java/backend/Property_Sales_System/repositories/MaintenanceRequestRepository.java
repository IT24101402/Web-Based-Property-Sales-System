package backend.Property_Sales_System.repositories;

import PropertyManagment.propertyease.backend.model.MaintenanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    // You can add custom query methods here, e.g.:
    // List<MaintenanceRequest> findByPropertyId(Long propertyId);
    // List<MaintenanceRequest> findByStatus(String status);
}