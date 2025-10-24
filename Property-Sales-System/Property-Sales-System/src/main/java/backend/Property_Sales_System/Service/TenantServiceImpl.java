package backend.Property_Sales_System.service;

import backend.Property_Sales_System.Repository.TenantRepository;
import backend.Property_Sales_System.dto.TenantRequest;
import backend.Property_Sales_System.dto.TenantResponse;
import backend.Property_Sales_System.exception.ResourceNotFoundException;
import backend.Property_Sales_System.model.Tenant;
import backend.Property_Sales_System.model.TenantStatus;
import backend.Property_Sales_System.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantServiceImpl implements TenantService {

    private final TenantRepository repo;

    private static TenantResponse toResponse(Tenant t) {
        return new TenantResponse(
                t.getId(), t.getFirstName(), t.getLastName(), t.getEmail(),
                t.getPhone(), t.getNationalId(), t.getDateOfBirth(),
                t.getAddressLine1(), t.getCity(), t.getState(), t.getPostalCode(),
                t.getMoveInDate(), t.getStatus()
        );
    }

    private static void copy(TenantRequest r, Tenant t) {
        t.setFirstName(r.firstName());
        t.setLastName(r.lastName());
        t.setEmail(r.email());
        t.setPhone(r.phone());
        t.setNationalId(r.nationalId());
        t.setDateOfBirth(r.dateOfBirth());
        t.setAddressLine1(r.addressLine1());
        t.setCity(r.city());
        t.setState(r.state());
        t.setPostalCode(r.postalCode());
        t.setMoveInDate(r.moveInDate());
        if (r.status() != null) t.setStatus(r.status());
    }

    @Override
    public TenantResponse create(TenantRequest request) {
        Tenant t = new Tenant();
        copy(request, t);
        return toResponse(repo.save(t));
    }

    @Override
    @Transactional(readOnly = true)
    public TenantResponse getById(Long id) {
        Tenant t = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + id));
        return toResponse(t);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantResponse> list(String q, TenantStatus status, Pageable pageable) {
        if (q != null && !q.isBlank()) {
            return repo
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(q, q, pageable)
                    .map(TenantServiceImpl::toResponse);
        }
        if (status != null) {
            return repo.findByStatus(status, pageable).map(TenantServiceImpl::toResponse);
        }
        return repo.findAll(pageable).map(TenantServiceImpl::toResponse);
    }

    @Override
    public TenantResponse update(Long id, TenantRequest request) {
        Tenant t = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + id));
        copy(request, t);
        return toResponse(repo.save(t));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Tenant not found: " + id);
        }
        repo.deleteById(id);
    }
}
