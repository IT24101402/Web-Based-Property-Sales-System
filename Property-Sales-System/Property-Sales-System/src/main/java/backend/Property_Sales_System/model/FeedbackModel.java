package backend.Property_Sales_System.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "feedback")
public class FeedbackModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Relationship with User ---
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"feedbackList", "phones", "password", "hibernateLazyInitializer", "handler"})
    private User user; // ✅ Prevents recursive loading of feedbackList

    // --- Fields ---
    @NotBlank(message = "User role is required")
    @Column(name = "user_role", nullable = false, length = 50)
    private String role;

    @NotBlank(message = "Feedback type is required")
    @Column(name = "feedback_type", nullable = false, length = 100)
    private String type;

    @NotBlank(message = "Subject is required")
    @Size(max = 255, message = "Subject must not exceed 255 characters")
    @Column(nullable = false, length = 255)
    private String subject;

    @NotBlank(message = "Description is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Rating is required")
    @Min(1) @Max(5)
    private Integer rating;

    @NotNull(message = "Date is required")
    @Column(name = "submission_date", nullable = false)
    private LocalDate date;

    @NotNull(message = "Consent is required")
    @AssertTrue(message = "You must agree to the collection and processing of feedback")
    private Boolean consentGiven;

    // --- Constructors ---
    public FeedbackModel() {}

    public FeedbackModel(String role, String type, String subject, String description,
                         Integer rating, LocalDate date, Boolean consentGiven) {
        this.role = role;
        this.type = type;
        this.subject = subject;
        this.description = description;
        this.rating = rating;
        this.date = date;
        this.consentGiven = consentGiven;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Boolean getConsentGiven() { return consentGiven; }
    public void setConsentGiven(Boolean consentGiven) { this.consentGiven = consentGiven; }
}