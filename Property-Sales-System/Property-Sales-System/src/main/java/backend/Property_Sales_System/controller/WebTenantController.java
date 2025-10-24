package backend.Property_Sales_System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebTenantController {

    @GetMapping({ "/tenants"})
    public String dashboard() {
        // must match a file under src/main/resources/templates/
        return "Tenant_Management_Dashboard";
    }

    @GetMapping("/tenants/add")
    public String addTenant() {
        return "Add_Tenant";
    }

    @GetMapping("/tenants/edit")
    public String editTenant() {
        return "Edit_Tenant_Records";
    }

    @GetMapping("/tenants/delete")
    public String deleteTenant() {
        return "Delete_Tenant_Record";
    }
}
