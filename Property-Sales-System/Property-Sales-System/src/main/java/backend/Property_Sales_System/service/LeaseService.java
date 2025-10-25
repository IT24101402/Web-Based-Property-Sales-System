package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.Lease;
import backend.Property_Sales_System.model.LeaseStatus;
import java.util.List;

public interface LeaseService {
    Lease createLease(Lease lease);
    Lease getLease(Long id);
    List<Lease> getAllLeases();
    List<Lease> getLeasesByStatus(LeaseStatus status);
    Lease updateLease(Long id, Lease lease);
    void deleteLease(Long id);
}
