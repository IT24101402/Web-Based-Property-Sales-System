package backend.Property_Sales_System.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ... other property fields (address, name, etc.) ...
    private String address;

    // ⚠️ CRITICAL: The mappedBy="property" MUST match the field name
    // 'private Property property;' in MaintenanceRequest.java
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MaintenanceRequest> maintenanceRequests = new HashSet<>();

    // ... other fields and relationships ...

    // ----------------------
    // Getters and Setters
    // ----------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Set<MaintenanceRequest> getMaintenanceRequests() {
        return maintenanceRequests;
    }

    public void setMaintenanceRequests(Set<MaintenanceRequest> maintenanceRequests) {
        this.maintenanceRequests = maintenanceRequests;
    }
}