package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.Property;
import backend.Property_Sales_System.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * PropertyServiceImpl
 * Concrete implementation of PropertyService.
 * Handles CRUD and search operations using PropertyRepository.
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    @Override
    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    @Override
    public List<Property> searchByCity(String city) {
        return propertyRepository.findByCityContainingIgnoreCase(city);
    }

    @Override
    public List<Property> searchByDistrict(String district) {
        return propertyRepository.findByDistrictContainingIgnoreCase(district);
    }
}
