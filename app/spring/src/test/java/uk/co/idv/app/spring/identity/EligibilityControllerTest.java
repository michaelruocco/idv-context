package uk.co.idv.app.spring.identity;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EligibilityControllerTest {

    private final CreateEligibility createEligibility = mock(CreateEligibility.class);

    private final EligibilityController controller = new EligibilityController(createEligibility);

    @Test
    void shouldCreateEligibility() {
        CreateEligibilityRequest request = mock(CreateEligibilityRequest.class);
        Eligibility expected = mock(Eligibility.class);
        given(createEligibility.create(request)).willReturn(expected);

        ResponseEntity<Eligibility> response = controller.createEligibility(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

}
