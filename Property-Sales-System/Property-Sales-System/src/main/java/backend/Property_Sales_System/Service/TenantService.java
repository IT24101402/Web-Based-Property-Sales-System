package backend.Property_Sales_System.service;

import backend.Property_Sales_System.dto.TenantRequest;
import backend.Property_Sales_System.dto.TenantResponse;
import backend.Property_Sales_System.model.TenantStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantService {
    TenantResponse create(TenantRequest request);
    TenantResponse getById(Long id);
    Page<TenantResponse> list(String q, TenantStatus status, Pageable pageable);
    TenantResponse update(Long id, TenantRequest request);
    void delete(Long id);
}
