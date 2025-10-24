package backend.Property_Sales_System.model.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tenants",
        indexes = {
                @Index(name = "idx_tenant_email", columnList = "email"),
                @Index(name = "idx_tenant_name", columnList = "first_name,last_name")
        })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @Column(name = "national_id")
    private String nationalId;

    private LocalDate dateOfBirth;

    private String addressLine1;
    private String city;
    private String state;
    private String postalCode;

    private LocalDate moveInDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TenantStatus status = TenantStatus.PENDING;
}
