package backend.Property_Sales_System.Repository;

import backend.Property_Sales_System.model.Tenant;
import backend.Property_Sales_System.model.TenantStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByEmail(String email);

    Page<Tenant> findByStatus(TenantStatus status, Pageable pageable);

    Page<Tenant> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName, Pageable pageable
    );
}
