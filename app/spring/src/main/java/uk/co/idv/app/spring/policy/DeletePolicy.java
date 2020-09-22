package uk.co.idv.app.spring.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyService;

import java.util.UUID;

@RequiredArgsConstructor
public class DeletePolicy<P extends Policy> {

    private final PolicyService<P> service;

    public ResponseEntity<Object> delete(UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
