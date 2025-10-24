package backend.Property_Sales_System.model.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "leases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // property table can be added later
    @Column(name = "property_address")
    private String propertyAddress;

    private LocalDate leaseStart;
    private LocalDate leaseEnd;
    private BigDecimal monthlyRent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaseStatus status; // ACTIVE or INACTIVE
}
