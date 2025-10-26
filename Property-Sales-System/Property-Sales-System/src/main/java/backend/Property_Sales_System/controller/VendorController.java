package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.Property;
import backend.Property_Sales_System.service.PropertyService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vendor")
public class VendorController {

    private final PropertyService propertyService;

    public VendorController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /**  Vendor Dashboard (shows property list) */
    @GetMapping("/dashboard")
    public String vendorDashboard(Authentication auth, Model model) {
        Long vendorId = getLoggedUserId(auth);
        List<Property> vendorProperties = propertyService.getAllProperties().stream()
                .filter(p -> p.getVendorId() != null && p.getVendorId().equals(vendorId))
                .toList();

        model.addAttribute("vendorProperties", vendorProperties);
        model.addAttribute("propertyCount", vendorProperties.size());
        return "dashboards/vendor_dashboard";
    }

    /**  List vendor’s properties separately (optional dedicated view) */
    @GetMapping("/properties")
    public String listVendorProperties(Authentication auth, Model model) {
        Long vendorId = getLoggedUserId(auth);
        model.addAttribute("properties", propertyService.getAllProperties().stream()
                .filter(p -> p.getVendorId() != null && p.getVendorId().equals(vendorId))
                .toList());
        return "vendor_properties";
    }

    /**  Show Add Property form */
    @GetMapping("/properties/new")
    public String addPropertyForm(Model model) {
        model.addAttribute("property", new Property());
        return "vendor_property_form";
    }

    /**  Handle Add Property submission */
    @PostMapping("/properties")
    public String saveVendorProperty(@ModelAttribute Property property, Authentication auth) {
        property.setVendorId(getLoggedUserId(auth));
        propertyService.saveProperty(property);
        return "redirect:/vendor/dashboard";
    }

    /** Temporary stub until you connect real user session */
    private Long getLoggedUserId(Authentication auth) {
        return 1L;
    }
}
