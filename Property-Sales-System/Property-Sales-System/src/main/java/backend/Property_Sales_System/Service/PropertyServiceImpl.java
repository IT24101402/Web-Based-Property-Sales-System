package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.Property;
import backend.Property_Sales_System.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * PropertyServiceImpl
 * Concrete implementation of PropertyService interface.
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /** ✅ Fetch all properties */
    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    /** ✅ Fetch property by ID */
    @Override
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    /** ✅ Save or update a property */
    @Override
    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    /** ✅ Delete property by ID */
    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    /** ✅ Search by city (case-insensitive) */
    @Override
    public List<Property> searchByCity(String city) {
        return propertyRepository.findByCityContainingIgnoreCase(city);
    }

    /** ✅ Search by district (case-insensitive) */
    @Override
    public List<Property> searchByDistrict(String district) {
        return propertyRepository.findByDistrictContainingIgnoreCase(district);
    }

    /** ✅ Vendor-specific property listing */
    @Override
    public List<Property> getPropertiesByVendorEmail(String vendorEmail) {
        return propertyRepository.findByVendorEmail(vendorEmail);
    }
}
