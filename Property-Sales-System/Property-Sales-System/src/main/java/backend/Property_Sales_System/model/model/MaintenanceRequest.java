package backend.Property_Sales_System.model.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_requests")
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String issue;

    // ✅ CORRECT MAPPING: Uses the Property object to manage the foreign key.
    // The @JoinColumn(name = "property_id") maps to the physical database column.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    // Assuming a relationship to a User who reported the issue
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by_user_id", nullable = false)
    private User requestedBy;

    @Column(nullable = false)
    private String status; // E.g., "Reported", "In Progress", "Completed"

    @Column(updatable = false)
    private LocalDateTime dateReported = LocalDateTime.now();

    // ----------------------
    // Constructors (Optional, but good practice)
    // ----------------------

    public MaintenanceRequest() {
    }

    // ----------------------
    // Getters and Setters
    // ----------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIssue() { return issue; }
    public void setIssue(String issue) { this.issue = issue; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }

    public User getRequestedBy() { return requestedBy; }
    public void setRequestedBy(User requestedBy) { this.requestedBy = requestedBy; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDateReported() { return dateReported; }
    public void setDateReported(LocalDateTime dateReported) { this.dateReported = dateReported; }
}
