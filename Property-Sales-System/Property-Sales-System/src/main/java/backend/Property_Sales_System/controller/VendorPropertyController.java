package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.Property;
import backend.Property_Sales_System.service.PropertyService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vendor/manage/properties")
public class VendorPropertyController {

    private final PropertyService propertyService;

    public VendorPropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /** ✅ List properties belonging to the logged-in vendor */
    @GetMapping
    public String listVendorProperties(Authentication auth, Model model) {
        String vendorEmail = (auth != null) ? auth.getName() : "guest@example.com";

        List<Property> vendorProps = propertyService.getPropertiesByVendorEmail(vendorEmail);

        model.addAttribute("vendorProperties", vendorProps);
        model.addAttribute("propertyCount", vendorProps.size());
        return "dashboards/vendor_dashboard";   // points to vendor_dashboard.html
    }

    /** ✅ Display add-new form */
    @GetMapping("/new")
    public String newProperty(Model model) {
        model.addAttribute("property", new Property());
        return "vendor_property_form";
    }

    /** ✅ Save or update property */
    @PostMapping("/save")
    public String saveProperty(@ModelAttribute Property property, Authentication auth) {
        if (auth != null) {
            property.setVendorEmail(auth.getName());
        }
        propertyService.saveProperty(property);
        return "redirect:/vendor/manage/properties";
    }

    /** ✅ Edit existing property */
    @GetMapping("/edit/{id}")
    public String editProperty(@PathVariable Long id, Model model) {
        Property property = propertyService.getPropertyById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        model.addAttribute("property", property);
        return "vendor_property_form";
    }

    /** ✅ Delete property */
    @GetMapping("/delete/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return "redirect:/vendor/manage/properties";
    }
}
