package backend.Property_Sales_System.dto;

import backend.Property_Sales_System.model.TenantStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record TenantRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email @NotBlank String email,
        @NotBlank String phone,
        String nationalId,
        LocalDate dateOfBirth,
        String addressLine1,
        String city,
        String state,
        String postalCode,
        LocalDate moveInDate,
        TenantStatus status
) {}
