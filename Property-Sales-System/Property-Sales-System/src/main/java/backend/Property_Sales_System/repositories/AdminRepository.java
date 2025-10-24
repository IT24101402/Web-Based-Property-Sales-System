package backend.Property_Sales_System.repositories;

import backend.Property_Sales_System.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Admin entity.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
