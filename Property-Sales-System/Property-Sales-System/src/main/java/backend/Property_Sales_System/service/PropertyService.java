package backend.Property_Sales_System.service;

import backend.Property_Sales_System.model.Property;
import backend.Property_Sales_System.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    private final PropertyRepository repo;

    public PropertyService(PropertyRepository repo) {
        this.repo = repo;
    }

    public List<Property> getAll() { return repo.findAll(); }
    public Property save(Property p) { return repo.save(p); }
    public Property getById(Long id) { return repo.findById(id).orElse(null); }
    public void delete(Long id) { repo.deleteById(id); }

    // Search methods
    public List<Property> searchByCity(String city) {
        return repo.findByCityContainingIgnoreCase(city);
    }

    public List<Property> searchByDistrict(String district) {
        return repo.findByDistrictContainingIgnoreCase(district);
    }
}
