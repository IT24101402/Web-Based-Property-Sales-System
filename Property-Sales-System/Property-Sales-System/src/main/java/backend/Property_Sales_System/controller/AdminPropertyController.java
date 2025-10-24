package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.Property;
import backend.Property_Sales_System.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Admin-level CRUD operations on Property entities.
 * Base route: /api/admin/properties
 */
@RestController
@RequestMapping("/api/admin/properties")
public class AdminPropertyController {

    private final PropertyService propertyService;

    @Autowired
    public AdminPropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /**
     * Create or update a property.
     */
    @PostMapping
    public ResponseEntity<Property> createOrUpdateProperty(@RequestBody Property property) {
        Property savedProperty = propertyService.saveProperty(property);
        return ResponseEntity.ok(savedProperty);
    }

    /**
     * Get all properties.
     */
    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    /**
     * Delete a property by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
