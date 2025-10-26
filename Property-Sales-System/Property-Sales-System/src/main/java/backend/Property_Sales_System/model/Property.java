package backend.Property_Sales_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Street is required")
    @Size(max = 150, message = "Street cannot exceed 150 characters")
    private String street;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @NotBlank(message = "District is required")
    @Size(max = 100, message = "District cannot exceed 100 characters")
    private String district;

    @Min(value = 1, message = "Price must be greater than 0")
    private double price;

    @NotBlank(message = "Property type is required")
    private String propertyType;
@Column(nullable = true)
private String vendorEmail;

public String getVendorEmail() {
    return vendorEmail;
}

public void setVendorEmail(String vendorEmail) {
    this.vendorEmail = vendorEmail;
}
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private String imageUrl;
    private String address;

    //  Added fields
    private Boolean isSold = false;
    private Long buyerId;
    private Long vendorId;  // <-- new

    private boolean sold = false;
    public boolean isSold() { return sold; }
    public void setSold(boolean sold) { this.sold = sold; }
    
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MaintenanceRequest> maintenanceRequests = new HashSet<>();

    // ---------------------- Getters & Setters ----------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Boolean getIsSold() { return isSold; }
    public void setIsSold(Boolean isSold) { this.isSold = isSold; }

    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public Set<MaintenanceRequest> getMaintenanceRequests() { return maintenanceRequests; }
    public void setMaintenanceRequests(Set<MaintenanceRequest> maintenanceRequests) { this.maintenanceRequests = maintenanceRequests; }
}
