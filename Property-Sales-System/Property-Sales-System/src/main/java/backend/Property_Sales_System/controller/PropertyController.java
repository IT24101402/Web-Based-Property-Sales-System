package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.model.Property;
import backend.Property_Sales_System.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService service;

    public PropertyController(PropertyService service) {
        this.service = service;
    }

    // ✅ List all properties
    @GetMapping
    public String list(Model model) {
        model.addAttribute("properties", service.getAllProperties());
        return "properties";
    }

    // ✅ Create new property form
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("property", new Property());
        return "property-form";
    }

    // ✅ Save property (create or update)
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Property property,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("property", property);
            return "property-form";
        }
        service.saveProperty(property);
        return "redirect:/properties";
    }

    // ✅ Edit property form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("property", service.getPropertyById(id).orElse(null));
        return "property-form";
    }

    // ✅ Delete property
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteProperty(id);
        return "redirect:/properties";
    }

    // ✅ Search by city or district
    @GetMapping("/search")
    public String search(@RequestParam(required = false) String city,
                         @RequestParam(required = false) String district,
                         Model model) {
        if (city != null && !city.isEmpty()) {
            model.addAttribute("properties", service.searchByCity(city));
        } else if (district != null && !district.isEmpty()) {
            model.addAttribute("properties", service.searchByDistrict(district));
        } else {
            model.addAttribute("properties", service.getAllProperties());
        }
        return "properties";
    }
}
