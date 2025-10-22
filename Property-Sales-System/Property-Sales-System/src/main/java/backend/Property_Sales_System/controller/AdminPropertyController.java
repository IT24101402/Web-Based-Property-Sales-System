package backend.Property_Sales_System.controller;

import PropertyManagment.propertyease.backend.model.Property;
import PropertyManagment.propertyease.backend.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Base mapping for all API CRUD operations
@RequestMapping("/api/admin/properties")
public class AdminPropertyController {

    private final PropertyService propertyService;

    @Autowired
    public AdminPropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // CREATE / UPDATE
    @PostMapping
    public ResponseEntity<Property> createOrUpdateProperty(@RequestBody Property property) {
        Property savedProperty = propertyService.saveProperty(property);
        return ResponseEntity.ok(savedProperty);
    }

    // READ ALL (API endpoint)
    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    // DELETE (Accessed by the hidden form in property_page.html)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}