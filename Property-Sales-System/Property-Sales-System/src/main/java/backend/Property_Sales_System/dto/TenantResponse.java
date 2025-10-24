package backend.Property_Sales_System.dto;

import backend.Property_Sales_System.model.TenantStatus;
import java.time.LocalDate;

public record TenantResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String nationalId,
        LocalDate dateOfBirth,
        String addressLine1,
        String city,
        String state,
        String postalCode,
        LocalDate moveInDate,
        TenantStatus status
) {}
