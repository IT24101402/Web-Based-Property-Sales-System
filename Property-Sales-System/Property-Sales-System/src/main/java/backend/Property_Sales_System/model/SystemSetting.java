package backend.Property_Sales_System.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_settings")
public class SystemSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String keyName;

    @Column(nullable = false, length = 255)
    private String value;

    @Column(length = 500)
    private String description;

    @Column(length = 100)
    private String updatedBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public SystemSetting() {}

    public SystemSetting(String keyName, String value, String description, String updatedBy) {
        this.keyName = keyName;
        this.value = value;
        this.description = description;
        this.updatedBy = updatedBy;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKeyName() { return keyName; }
    public void setKeyName(String keyName) { this.keyName = keyName; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "SystemSetting{" +
                "id=" + id +
                ", keyName='" + keyName + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
