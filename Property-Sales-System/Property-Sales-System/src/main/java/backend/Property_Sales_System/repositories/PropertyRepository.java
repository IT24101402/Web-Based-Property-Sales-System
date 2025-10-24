package backend.Property_Sales_System.repositories;

import backend.Property_Sales_System.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByCityContainingIgnoreCase(String city);
    List<Property> findByDistrictContainingIgnoreCase(String district);
}
