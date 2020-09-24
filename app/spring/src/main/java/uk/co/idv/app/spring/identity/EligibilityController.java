package uk.co.idv.app.spring.identity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eligibility")
public class EligibilityController {

    private final CreateEligibility createEligibility;

    @PostMapping
    public ResponseEntity<IdentityEligibility> createEligibility(@RequestBody CreateEligibilityRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createEligibility.create(request));
    }

}
