package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.dto.TenantRequest;
import backend.Property_Sales_System.dto.TenantResponse;
import backend.Property_Sales_System.model.TenantStatus;
import backend.Property_Sales_System.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


//create
@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TenantResponse create(@Valid @RequestBody TenantRequest request) {
        return service.create(request);
    }
//read
    @GetMapping("/{id}")
    public TenantResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<TenantResponse> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) TenantStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(q, status, pageable);
    }
//update
    @PutMapping("/{id}")
    public TenantResponse update(@PathVariable Long id,
                                 @Valid @RequestBody TenantRequest request) {
        return service.update(id, request);
    }
//delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
