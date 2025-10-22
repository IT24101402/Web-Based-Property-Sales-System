package backend.Property_Sales_System.model.model;

import jakarta.persistence.*;

@Entity
@Table(name = "system_settings")
public class SystemSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyName;
    private String value;
    private String description;
    private String updatedBy;

    // Getters and setters
}
