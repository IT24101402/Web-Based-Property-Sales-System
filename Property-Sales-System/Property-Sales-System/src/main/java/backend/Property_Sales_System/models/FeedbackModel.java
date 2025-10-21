package backend.Property_Sales_System.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "feedback")
public class FeedbackModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "User role is required")
    @Column(name = "user_role", nullable = false, length = 50)
    private String role;

    @NotBlank(message = "Feedback type is required")
    @Column(name = "feedback_type", nullable = false, length = 100)
    private String type;

    @NotBlank(message = "Subject is required")
    @Size(max = 255, message = "Subject must not exceed 255 characters")
    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    @NotBlank(message = "Description is required")
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @NotNull(message = "Date is required")
    @Column(name = "submission_date", nullable = false)
    private LocalDate date;

    @NotNull(message = "Consent is required")
    @AssertTrue(message = "You must agree to the collection and processing of feedback")
    @Column(name = "consent_given", nullable = false)
    private Boolean consentGiven;

    public FeedbackModel() {
    }

    public FeedbackModel(String role, String type, String subject, String description, Integer rating, LocalDate date, Boolean consentGiven) {
        this.role = role;
        this.type = type;
        this.subject = subject;
        this.description = description;
        this.rating = rating;
        this.date = date;
        this.consentGiven = consentGiven;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getConsentGiven() {
        return consentGiven;
    }

    public void setConsentGiven(Boolean consentGiven) {
        this.consentGiven = consentGiven;
    }

    @Override
    public String toString() {
        return "FeedbackModel{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", type='" + type + '\'' +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", date=" + date +
                ", consentGiven=" + consentGiven +
                '}';
    }
}