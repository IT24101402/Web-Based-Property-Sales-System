package backend.Property_Sales_System.controller;

import backend.Property_Sales_System.service.LeaseService; // <- this import must match the interface above
import backend.Property_Sales_System.model.Lease;
import backend.Property_Sales_System.model.LeaseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leases")
@RequiredArgsConstructor
public class LeaseController {

    private final LeaseService leaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lease create(@RequestBody Lease lease) {
        return leaseService.createLease(lease);
    }

    @GetMapping("/{id}")
    public Lease get(@PathVariable Long id) {
        return leaseService.getLease(id);
    }

    @GetMapping
    public List<Lease> list(@RequestParam(required = false) LeaseStatus status) {
        return status == null ? leaseService.getAllLeases()
                : leaseService.getLeasesByStatus(status);
    }

    @PutMapping("/{id}")
    public Lease update(@PathVariable Long id, @RequestBody Lease lease) {
        return leaseService.updateLease(id, lease);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        leaseService.deleteLease(id);
    }
}
