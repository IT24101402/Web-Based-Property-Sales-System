package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.Property;

import java.util.List;
import java.util.Optional;

/**
 * PropertyService Interface
 * Defines the contract for property management operations.
 */
public interface PropertyService {

    List<Property> getAllProperties();

    Optional<Property> getPropertyById(Long id);

    Property saveProperty(Property property);

    void deleteProperty(Long id);

    List<Property> searchByCity(String city);

    List<Property> searchByDistrict(String district);

    List<Property> getPropertiesByVendorEmail(String vendorEmail);
}
