package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.Property;

import java.util.List;
import java.util.Optional;

/**
 * PropertyService
 * Interface defining core CRUD operations for Property entities.
 */
public interface PropertyService {

    /**
     * Retrieve all properties.
     */
    List<Property> getAllProperties();

    /**
     * Retrieve a property by its ID.
     */
    Optional<Property> getPropertyById(Long id);

    /**
     * Save or update a property.
     */
    Property saveProperty(Property property);

    /**
     * Delete a property by ID.
     */
    void deleteProperty(Long id);

    /**
     * Search properties by city (case-insensitive match).
     */
    List<Property> searchByCity(String city);

    /**
     * Search properties by district (case-insensitive match).
     */
    List<Property> searchByDistrict(String district);
}
