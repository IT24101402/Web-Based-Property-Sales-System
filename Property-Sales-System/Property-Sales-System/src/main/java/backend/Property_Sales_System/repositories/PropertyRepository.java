package backend.Property_Sales_System.repositories;

import PropertyManagment.propertyease.backend.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    // Spring Data JPA provides all standard CRUD methods automatically.
}