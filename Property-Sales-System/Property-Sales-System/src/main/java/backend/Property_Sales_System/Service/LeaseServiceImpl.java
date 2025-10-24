package backend.Property_Sales_System.service;

import backend.Property_Sales_System.Repository.LeaseRepository;
import backend.Property_Sales_System.exception.ResourceNotFoundException;
import backend.Property_Sales_System.model.Lease;
import backend.Property_Sales_System.model.LeaseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaseServiceImpl implements backend.Property_Sales_System.Service.LeaseService {

    private final LeaseRepository leaseRepository;

    @Override
    public Lease createLease(Lease lease) {
        return leaseRepository.save(lease);
    }

    @Override
    @Transactional(readOnly = true)
    public Lease getLease(Long id) {
        return leaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lease not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lease> getLeasesByStatus(LeaseStatus status) {
        return leaseRepository.findByStatus(status);
    }

    @Override
    public Lease updateLease(Long id, Lease lease) {
        Lease existing = getLease(id);           // throws 404 if missing
        lease.setId(existing.getId());           // ensure we update the same row
        return leaseRepository.save(lease);
    }

    @Override
    public void deleteLease(Long id) {
        if (!leaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lease not found: " + id);
        }
        leaseRepository.deleteById(id);
    }
}
